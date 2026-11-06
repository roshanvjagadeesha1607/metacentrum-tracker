package com.mc.tracker

import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.mc.tracker.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {
    private var ringtone: Ringtone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            @Suppress("DEPRECATION")
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            )
        }

        val jobName = intent.getStringExtra("jobName") ?: "job"
        val jobId = intent.getStringExtra("jobId") ?: ""
        binding.textJobInfo.text = "$jobName\n$jobId"

        val uri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(this, uri)
        ringtone?.play()

        binding.buttonDismiss.setOnClickListener {
            ringtone?.stop()
            finish()
        }
    }

    override fun onDestroy() {
        ringtone?.stop()
        super.onDestroy()
    }
}
