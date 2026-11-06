package com.mc.tracker.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mc.tracker.R
import com.mc.tracker.model.Job

sealed class Row {
    data class Header(val title: String) : Row()
    data class JobRow(val job: Job) : Row()
}

class JobAdapter(private var rows: List<Row>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_JOB = 1
    }

    fun update(newRows: List<Row>) {
        rows = newRows
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int =
        when (rows[position]) {
            is Row.Header -> TYPE_HEADER
            is Row.JobRow -> TYPE_JOB
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_HEADER) {
            val v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            HeaderVH(v)
        } else {
            val v = inflater.inflate(R.layout.item_job, parent, false)
            JobVH(v)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val row = rows[position]) {
            is Row.Header -> (holder as HeaderVH).bind(row.title)
            is Row.JobRow -> (holder as JobVH).bind(row.job)
        }
    }

    override fun getItemCount() = rows.size

    class HeaderVH(view: View) : RecyclerView.ViewHolder(view) {
        private val text = view.findViewById<TextView>(android.R.id.text1)
        fun bind(title: String) {
            text.text = title
            text.setTypeface(null, Typeface.BOLD)
            text.textSize = 18f
        }
    }

    class JobVH(view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.textJobName)
        private val details = view.findViewById<TextView>(R.id.textJobDetails)
        private val statusDot = view.findViewById<View>(R.id.viewStatusDot)

        fun bind(job: Job) {
            name.text = "${job.name}  (${job.jobId})"
            val usedH = job.walltimeUsedSec / 3600
            val reqH = job.walltimeRequestedSec / 3600
            details.text = "Queue: ${job.queue}   Walltime: ${usedH}h / ${reqH}h"

            val color = when (job.state) {
                "R" -> R.color.colorRunning
                "Q" -> R.color.colorQueued
                "C" -> R.color.colorCompleted
                "H" -> R.color.colorHeld
                else -> R.color.colorQueued
            }
            statusDot.setBackgroundColor(itemView.context.getColor(color))
        }
    }
}
