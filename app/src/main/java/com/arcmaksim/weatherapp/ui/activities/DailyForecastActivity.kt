package com.arcmaksim.weatherapp.ui.activities

import android.app.ListActivity
import android.os.Bundle
import com.arcmaksim.weatherapp.R

class DailyForecastActivity : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_forecast)

        val daysOfTheWeek = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    }

}
