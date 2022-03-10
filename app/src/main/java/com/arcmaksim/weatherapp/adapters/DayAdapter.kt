package com.arcmaksim.weatherapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arcmaksim.weatherapp.R
import com.arcmaksim.weatherapp.models.Day

class DayAdapter(var mContext: Context, var mDays: Array<Day>) : BaseAdapter() {

    override fun getItem(position: Int): Any {
        return mDays[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return mDays.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item, null)
            holder = ViewHolder(view)
            view!!.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val item = getItem(position)
        holder.temperatureView.text = (item as Day).temperature.toString()
        if (position == 0) {
            holder.dayNameView.text = "Today"
        } else {
            holder.dayNameView.text = item.dayOfTheWeek
        }
        holder.iconView.setImageResource(item.iconId)

        return view
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val iconView: ImageView = itemView.findViewById(R.id.iconImageView)
        val temperatureView: TextView = itemView.findViewById(R.id.dayNameLabel)
        val dayNameView: TextView = itemView.findViewById(R.id.dayNameLabel)

    }

}
