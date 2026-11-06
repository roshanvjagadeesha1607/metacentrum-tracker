package com.mc.tracker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mc.tracker.adapter.JobAdapter
import com.mc.tracker.adapter.Row
import com.mc.tracker.databinding.ActivityMainBinding
import com.mc.tracker.parser.JobParser
import com.mc.tracker.ssh.SshJobFetcher
import com.mc.tracker.util.Prefs
import com.mc.tracker.worker.JobCheckWorker
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: JobAdapter

    private val notifPermLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = JobAdapter(emptyList())
        binding.recyclerJobs.layoutManager = LinearLayoutManager(this)
        binding.recyclerJobs.adapter = adapter

        binding.buttonSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.swipeRefresh.setOnRefreshListener { refreshNow() }

        requestNotifPermissionIfNeeded()

        if (!Prefs.isConfigured(this)) {
            startActivity(Intent(this, SettingsActivity::class.java))
        } else {
            schedulePeriodicCheck()
            loadCachedJobs()
            refreshNow()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Prefs.isConfigured(this)) loadCachedJobs()
    }

    private fun requestNotifPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                notifPermLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun schedulePeriodicCheck() {
        val request = PeriodicWorkRequestBuilder<JobCheckWorker>(15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "job_check", ExistingPeriodicWorkPolicy.KEEP, request
        )
    }

    private fun refreshNow() {
        binding.swipeRefresh.isRefreshing = true
        thread {
            try {
                val host = Prefs.getHost(this)
                val username = Prefs.getUsername(this)
                val key = Prefs.getPrivateKey(this)
                val raw = SshJobFetcher(host, username, key).fetchQstatOutput()
                Prefs.saveLastJobsRaw(this, raw)
                runOnUiThread {
                    renderJobs(raw)
                    binding.swipeRefresh.isRefreshing = false
                }
            } catch (e: Exception) {
                runOnUiThread {
                    binding.swipeRefresh.isRefreshing = false
                    binding.textError.text = "Error: ${e.message}"
                    binding.textError.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun loadCachedJobs() {
        val raw = Prefs.getLastJobsRaw(this)
        if (raw.isNotBlank()) renderJobs(raw)
    }

    private fun renderJobs(raw: String) {
        val jobs = JobParser.parse(raw)
        val running = jobs.filter { it.state == "R" }
        val queued = jobs.filter { it.state == "Q" }
        val completed = jobs.filter { it.state == "C" }

        val rows = mutableListOf<Row>()
        rows.add(Row.Header("Running (${running.size})"))
        running.forEach { rows.add(Row.JobRow(it)) }
        rows.add(Row.Header("Queued (${queued.size})"))
        queued.forEach { rows.add(Row.JobRow(it)) }
        rows.add(Row.Header("Completed (${completed.size})"))
        completed.forEach { rows.add(Row.JobRow(it)) }

        adapter.update(rows)
        binding.textError.visibility = View.GONE
    }
}
