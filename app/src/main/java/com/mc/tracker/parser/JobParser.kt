package com.mc.tracker.parser

import com.mc.tracker.model.Job

/**
 * Parses the output of `qstat -f -u <username>` (PBS Pro / OpenPBS full format)
 * into a list of Job objects.
 */
object JobParser {

    fun parse(raw: String): List<Job> {
        val jobs = mutableListOf<Job>()

        var jobId: String? = null
        var name = ""
        var state = ""
        var queue = ""
        var reqWalltime = 0L
        var usedWalltime = 0L

        fun flush() {
            val id = jobId
            if (id != null) {
                jobs.add(Job(id, name, state, queue, reqWalltime, usedWalltime))
            }
            jobId = null
            name = ""
            state = ""
            queue = ""
            reqWalltime = 0L
            usedWalltime = 0L
        }

        for (rawLine in raw.lines()) {
            val line = rawLine.trim()

            if (line.startsWith("Job Id:")) {
                flush()
                jobId = line.substringAfter("Job Id:").trim()
                continue
            }

            if (!line.contains("=")) continue
            val parts = line.split("=", limit = 2)
            if (parts.size < 2) continue

            val key = parts[0].trim()
            val value = parts[1].trim()

            when (key) {
                "Job_Name" -> name = value
                "job_state" -> state = value
                "queue" -> queue = value
                "Resource_List.walltime" -> reqWalltime = parseHms(value)
                "resources_used.walltime" -> usedWalltime = parseHms(value)
            }
        }
        flush()
        return jobs
    }

    private fun parseHms(hms: String): Long {
        val p = hms.split(":")
        return try {
            when (p.size) {
                3 -> p[0].toLong() * 3600 + p[1].toLong() * 60 + p[2].toLong()
                2 -> p[0].toLong() * 60 + p[1].toLong()
                else -> 0L
            }
        } catch (e: Exception) {
            0L
        }
    }
}
