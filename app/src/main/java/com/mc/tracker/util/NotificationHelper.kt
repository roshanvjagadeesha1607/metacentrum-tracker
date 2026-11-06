package com.mc.tracker.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.mc.tracker.AlarmActivity

object NotificationHelper {
    const val CHANNEL_ALARM = "job_alarm"
    const val CHANNEL_COMPLETE = "job_complete"

    fun ensureChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = context.getSystemService(NotificationManager::class.java)
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

            val alarmChannel = NotificationChannel(
                CHANNEL_ALARM, "Walltime alarms", NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setSound(
                    alarmSound,
                    AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()
                )
                enableVibration(true)
            }

            val completeChannel = NotificationChannel(
                CHANNEL_COMPLETE, "Job completed", NotificationManager.IMPORTANCE_DEFAULT
            )

            nm.createNotificationChannel(alarmChannel)
            nm.createNotificationChannel(completeChannel)
        }
    }

    fun showWalltimeAlarm(context: Context, jobId: String, jobName: String, remainingMinutes: Long) {
        ensureChannels(context)

        val fullScreenIntent = Intent(context, AlarmActivity::class.java).apply {
            putExtra("jobId", jobId)
            putExtra("jobName", jobName)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val fullScreenPendingIntent = PendingIntent.getActivity(
            context, jobId.hashCode(), fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ALARM)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("Walltime almost up: $jobName")
            .setContentText("Only ~$remainingMinutes min of walltime left for job $jobId")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setAutoCancel(true)
            .build()

        val nm = context.getSystemService(NotificationManager::class.java)
        nm.notify(jobId.hashCode(), notification)
    }

    fun showJobCompleted(context: Context, jobId: String, jobName: String) {
        ensureChannels(context)

        val notification = NotificationCompat.Builder(context, CHANNEL_COMPLETE)
            .setSmallIcon(android.R.drawable.stat_sys_download_done)
            .setContentTitle("Job completed")
            .setContentText("$jobName ($jobId) has finished")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        val nm = context.getSystemService(NotificationManager::class.java)
        nm.notify((jobId + "_done").hashCode(), notification)
    }
}
