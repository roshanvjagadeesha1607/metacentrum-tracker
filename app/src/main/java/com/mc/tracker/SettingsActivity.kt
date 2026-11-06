package com.mc.tracker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.mc.tracker.databinding.ActivitySettingsBinding
import com.mc.tracker.util.Prefs

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    private val filePicker = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let {
            contentResolver.openInputStream(it)?.bufferedReader()?.use { reader ->
                binding.editKey.setText(reader.readText())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editHost.setText(Prefs.getHost(this))
        binding.editUsername.setText(Prefs.getUsername(this))
        binding.editKey.setText(Prefs.getPrivateKey(this))
        binding.editThreshold.setText(Prefs.getThresholdMinutes(this).toString())

        binding.buttonBrowseKey.setOnClickListener {
            filePicker.launch(arrayOf("*/*"))
        }

        binding.buttonSave.setOnClickListener {
            val host = binding.editHost.text.toString().trim().ifBlank { "ssh.metacentrum.cz" }
            val username = binding.editUsername.text.toString().trim()
            val key = binding.editKey.text.toString().trim()
            val threshold = binding.editThreshold.text.toString().toIntOrNull() ?: 60

            Prefs.saveConfig(this, host, username, key, threshold)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
