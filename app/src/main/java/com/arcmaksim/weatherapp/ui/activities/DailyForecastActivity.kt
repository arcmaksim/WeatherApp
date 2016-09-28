package com.arcmaksim.weatherapp.ui.activities

import android.app.ListActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.arcmaksim.weatherapp.R
import com.arcmaksim.weatherapp.adapters.DayAdapter
import com.arcmaksim.weatherapp.models.Day
import java.util.*

class DailyForecastActivity : ListActivity() {

    lateinit var mDays: Array<Day>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_forecast)

        val parcelables: Array<Parcelable> = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST)
        mDays = Arrays.copyOf(parcelables, parcelables.size, Array<Day>::class.java)
        val adapter: DayAdapter = DayAdapter(this, mDays)
        listAdapter = adapter
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

        val message = String.format("On %s the high will be %s and it will be %s",
                mDays[position].getDayOfTheWeek(),
                mDays[position].getTemperature(),
                mDays[position].mSummary)
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}
