package com.arcmaksim.weatherapp.ui.activities

import android.app.ListActivity
import android.os.Bundle
import android.os.Parcelable
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

}
