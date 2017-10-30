package com.arcmaksim.weatherapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.arcmaksim.weatherapp.models.Day
import kotlinx.android.extensions.CacheImplementation
import kotlinx.android.extensions.ContainerOptions
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.daily_list_item.*

class DayAdapter(val context: Context, var days: Array<Day>) : BaseAdapter() {

	override fun getItem(position: Int) = days[position]


	override fun getItemId(position: Int) = 0L


	override fun getCount() = days.size


	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		var view = convertView
		val holder: ViewHolder

		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.daily_list_item, null)
			holder = ViewHolder(view)
			view!!.tag = holder
		} else {
			holder = view.tag as ViewHolder
		}

		val item = getItem(position)


		return view
	}


	@ContainerOptions(CacheImplementation.HASH_MAP)
	class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
			LayoutContainer {

		fun bindView(day: Day) {
			temperatureLabel?.text = day.getTemperature().toString()
			dayNameLabel?.text =
					if (adapterPosition == 0) {
						"Today"
					} else {
						day.getDayOfTheWeek()
					}
			iconImageView?.setImageResource(day.getIconId())
		}

	}
}