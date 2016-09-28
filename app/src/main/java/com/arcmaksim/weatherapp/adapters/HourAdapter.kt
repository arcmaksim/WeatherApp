package com.arcmaksim.weatherapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.arcmaksim.weatherapp.R
import com.arcmaksim.weatherapp.models.Hour

class HourAdapter(var mHours: Array<Hour>) : RecyclerView.Adapter<HourAdapter.HourViewHolder>() {

    override fun getItemCount(): Int {
        return mHours.size
    }

    override fun onBindViewHolder(holder: HourViewHolder?, position: Int) {
        holder?.bindHour(mHours[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HourViewHolder {
        val view: View = LayoutInflater.from(parent?.context)
                .inflate(R.layout.hourly_list_item, parent, false)
        val viewHolder: HourViewHolder = HourViewHolder(view)
        return viewHolder
    }

    class HourViewHolder : RecyclerView.ViewHolder {

        @BindView(R.id.timeLabel)
        lateinit var timeView: TextView

        @BindView(R.id.iconImageView)
        lateinit var iconView: ImageView

        @BindView(R.id.summaryLabel)
        lateinit var summaryView: TextView

        @BindView(R.id.temperatureLabel)
        lateinit var temperatureView: TextView

        constructor(itemView: View) : super(itemView) {
            ButterKnife.bind(itemView)
        }

        fun bindHour(hour: Hour) {
            timeView.text = hour.getFormattedTime()
            iconView.setImageResource(hour.getIconId())
            summaryView.text = hour.mSummary
            temperatureView.text = hour.getTemperature().toString()
        }

    }

}