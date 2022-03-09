package com.arcmaksim.weatherapp.ui.activities

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arcmaksim.weatherapp.R
import com.arcmaksim.weatherapp.adapters.HourAdapter
import com.arcmaksim.weatherapp.models.Hour
import java.util.*

class HourlyForecastActivity : ComponentActivity(R.layout.activity_hourly_forecast) {

    companion object {
        @JvmStatic
        val TAG = HourlyForecastActivity::class.java.simpleName
    }

    lateinit var mHours: Array<Hour>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val parcelables: Array<Parcelable> = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST) as Array<Parcelable>
        mHours = Arrays.copyOf(parcelables, parcelables.size, Array<Hour>::class.java)

        val adapter = HourAdapter(mHours)
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
    }
}
