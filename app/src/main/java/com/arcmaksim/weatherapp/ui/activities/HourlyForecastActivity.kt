package com.arcmaksim.weatherapp.ui.activities

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import com.arcmaksim.weatherapp.R
import com.arcmaksim.weatherapp.models.Hour
import java.util.*

class HourlyForecastActivity : AppCompatActivity() {

    lateinit var mHours: Array<Hour>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hourly_forecast)

        val parcelables: Array<Parcelable> = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST)
        mHours = Arrays.copyOf(parcelables, parcelables.size, Array<Hour>::class.java)
    }
}
