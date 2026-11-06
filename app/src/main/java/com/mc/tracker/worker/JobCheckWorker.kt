package com.mc.tracker.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mc.tracker.parser.JobParser
import com.mc.tracker.ssh.SshJobFetcher
import com.mc.tracker.util.NotificationHelper
import com.mc.tracker.util.Prefs

/**
 * Runs on a background schedule (every 15 minutes, the minimum WorkManager allows
 * for periodic work). Connects over SSH, checks job states, and:
 *  - fires a full-screen alarm when a running job's walltime is about to run out
 *  - sends a notification when a job completes (either seen with state C, or
 *    it has disappeared entirely from qstat output, meaning PBS already purged it)
 */
class JobCheckWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val context = applicationContext
        if (!Prefs.isConfigured(context)) return Result.success()

        return try {
            val host = Prefs.getHost(context)
            val username = Prefs.getUsername(context)
            val key = Prefs.getPrivateKey(context)
            val thresholdMinutes = Prefs.getThresholdMinutes(context)

            val raw = SshJobFetcher(host, username, key).fetchQstatOutput()
            val jobs = JobParser.parse(raw)

            val currentIds = jobs.map { it.jobId }.toSet()
            val knownIds = Prefs.getKnownJobs(context)
            val alarmed = Prefs.getAlarmedJobs(context).toMutableSet()
            val completedNotified = Prefs.getCompletedNotified(context).toMutableSet()

            for (job in jobs) {
                if (job.state == "R") {
                    val remainingMin = job.remainingSec() / 60
                    if (remainingMin in 0..thresholdMinutes.toLong() && job.jobId !in alarmed) {
                        NotificationHelper.showWalltimeAlarm(context, job.jobId, job.name, remainingMin)
                        alarmed.add(job.jobId)
                    }
                }
                if (job.state == "C" && job.jobId !in completedNotified) {
                    NotificationHelper.showJobCompleted(context, job.jobId, job.name)
                    completedNotified.add(job.jobId)
                }
            }

            // jobs that vanished entirely from qstat output (already purged by PBS) = completed
            val vanished = knownIds - currentIds
            for (id in vanished) {
                if (id !in completedNotified) {
                    NotificationHelper.showJobCompleted(context, id, id)
                    completedNotified.add(id)
                }
            }

            Prefs.saveKnownJobs(context, currentIds)
            Prefs.saveAlarmedJobs(context, alarmed)
            Prefs.saveCompletedNotified(context, completedNotified)
            Prefs.saveLastJobsRaw(context, raw)

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
