package com.arcmaksim.weatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arcmaksim.weatherapp.R
import com.arcmaksim.weatherapp.models.Hour

class HourAdapter(var mHours: Array<Hour>) : RecyclerView.Adapter<HourAdapter.HourViewHolder>() {

    override fun getItemCount(): Int {
        return mHours.size
    }

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        holder.bindHour(mHours[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.hourly_list_item, parent, false)
        val viewHolder = HourViewHolder(view)
        return viewHolder
    }

    class HourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val timeView: TextView = itemView.findViewById(R.id.timeLabel)
        val iconView: ImageView = itemView.findViewById(R.id.iconImageView)
        val summaryView: TextView = itemView.findViewById(R.id.summaryLabel)
        val temperatureView: TextView = itemView.findViewById(R.id.temperatureLabel)

        fun bindHour(hour: Hour) {
            timeView.text = hour.getFormattedTime()
            iconView.setImageResource(hour.getIconId())
            summaryView.text = hour.mSummary
            temperatureView.text = hour.getTemperature().toString()
        }

    }

}
