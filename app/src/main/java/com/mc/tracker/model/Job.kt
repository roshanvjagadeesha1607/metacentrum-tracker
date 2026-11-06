package com.mc.tracker.model

data class Job(
    val jobId: String,
    val name: String,
    val state: String, // Q, R, C, H, E ...
    val queue: String,
    val walltimeRequestedSec: Long,
    val walltimeUsedSec: Long
) {
    fun remainingSec(): Long = (walltimeRequestedSec - walltimeUsedSec).coerceAtLeast(0)
}
