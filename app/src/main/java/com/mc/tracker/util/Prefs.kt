package com.mc.tracker.util

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object Prefs {
    private const val FILE_NAME = "secure_prefs"

    private fun prefs(context: Context) = EncryptedSharedPreferences.create(
        context,
        FILE_NAME,
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveConfig(context: Context, host: String, username: String, privateKey: String, thresholdMinutes: Int) {
        prefs(context).edit()
            .putString("host", host)
            .putString("username", username)
            .putString("privateKey", privateKey)
            .putInt("thresholdMinutes", thresholdMinutes)
            .apply()
    }

    fun getHost(context: Context) = prefs(context).getString("host", "ssh.metacentrum.cz") ?: "ssh.metacentrum.cz"
    fun getUsername(context: Context) = prefs(context).getString("username", "") ?: ""
    fun getPrivateKey(context: Context) = prefs(context).getString("privateKey", "") ?: ""
    fun getThresholdMinutes(context: Context) = prefs(context).getInt("thresholdMinutes", 60)

    fun isConfigured(context: Context) = getUsername(context).isNotBlank() && getPrivateKey(context).isNotBlank()

    fun saveLastJobsRaw(context: Context, raw: String) {
        prefs(context).edit().putString("lastJobsRaw", raw).apply()
    }

    fun getLastJobsRaw(context: Context): String =
        prefs(context).getString("lastJobsRaw", "") ?: ""

    // job ids we've already fired a walltime alarm for (avoid repeat alarms every check)
    fun getAlarmedJobs(context: Context): MutableSet<String> =
        prefs(context).getStringSet("alarmedJobs", emptySet())?.toMutableSet() ?: mutableSetOf()

    fun saveAlarmedJobs(context: Context, set: Set<String>) {
        prefs(context).edit().putStringSet("alarmedJobs", set).apply()
    }

    // full set of job ids seen on the previous check (to detect ones that vanished = completed)
    fun getKnownJobs(context: Context): MutableSet<String> =
        prefs(context).getStringSet("knownJobs", emptySet())?.toMutableSet() ?: mutableSetOf()

    fun saveKnownJobs(context: Context, set: Set<String>) {
        prefs(context).edit().putStringSet("knownJobs", set).apply()
    }

    // job ids we've already sent a "completed" notification for
    fun getCompletedNotified(context: Context): MutableSet<String> =
        prefs(context).getStringSet("completedNotified", emptySet())?.toMutableSet() ?: mutableSetOf()

    fun saveCompletedNotified(context: Context, set: Set<String>) {
        prefs(context).edit().putStringSet("completedNotified", set).apply()
    }
}
