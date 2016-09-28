package com.arcmaksim.weatherapp.ui.activities

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import com.arcmaksim.weatherapp.R
import com.arcmaksim.weatherapp.adapters.HourAdapter
import com.arcmaksim.weatherapp.models.Hour
import java.util.*

class HourlyForecastActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        val TAG: String = HourlyForecastActivity::class.java.simpleName
    }

    lateinit var mHours: Array<Hour>

    @BindView(R.id.recyclerView) lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hourly_forecast)
        ButterKnife.bind(this)

        Log.i(TAG, TAG)

        val parcelables: Array<Parcelable> = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST)
        mHours = Arrays.copyOf(parcelables, parcelables.size, Array<Hour>::class.java)

        val adapter = HourAdapter(mHours)
        mRecyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.setHasFixedSize(true)
    }
}
