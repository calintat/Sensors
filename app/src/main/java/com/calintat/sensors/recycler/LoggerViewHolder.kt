package com.calintat.sensors.recycler

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.calintat.sensors.R
import com.calintat.sensors.api.Logger
import org.jetbrains.anko.find

class LoggerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val time = itemView.find<TextView>(R.id.logger_list_item_time)

    private val data = itemView.find<TextView>(R.id.logger_list_item_data)

    fun bindItem(item: Logger.Snapshot) {

        time.text = item.printTimeInSeconds()

        data.text = item.data.joinToString(separator = "\n")
    }
}