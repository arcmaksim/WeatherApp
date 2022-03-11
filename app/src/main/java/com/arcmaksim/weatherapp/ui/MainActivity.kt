package com.arcmaksim.weatherapp.ui

import androidx.activity.ComponentActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.arcmaksim.weatherapp.App
import com.arcmaksim.weatherapp.R
import com.arcmaksim.weatherapp.domain.model.ForecastRecord
import com.arcmaksim.weatherapp.ui.currentforecast.ICurrentForecastViewModel
import com.arcmaksim.weatherapp.ui.dailyforecast.DailyForecastArgs
import com.arcmaksim.weatherapp.ui.hourlyforecast.HourlyForecastArgs
import com.arcmaksim.weatherapp.ui.dailyforecast.DailyForecastActivity
import com.arcmaksim.weatherapp.ui.hourlyforecast.HourlyForecastActivity
import com.arcmaksim.weatherapp.ui.fragments.AlertDialogFragment
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class MainActivity : ComponentActivity(R.layout.activity_main) {

    companion object {
        const val DAILY_FORECAST = "DAILY_FORECAST"
        const val HOURLY_FORECAST = "HOURLY_FORECAST"
    }

    lateinit var mTemperatureView: TextView
    lateinit var mHumidityView: TextView
    lateinit var mPrecipView: TextView
    lateinit var mSummaryView: TextView
    lateinit var mTimeView: TextView
    lateinit var mIconView: ImageView
    lateinit var mRefreshView: ImageView
    lateinit var mProgressBar: ProgressBar
    lateinit var hourlyButton: Button
    lateinit var dailyButton: Button

    @Inject
    lateinit var viewModel: ICurrentForecastViewModel

    override fun onCreate(
        savedInstanceState: Bundle?,
    ) {
        (application as App).forecastComponent.inject(this)

        super.onCreate(savedInstanceState)

        mTemperatureView = findViewById(R.id.temperatureLabel)
        mHumidityView = findViewById(R.id.humidityValue)
        mPrecipView = findViewById(R.id.precipValue)
        mSummaryView = findViewById(R.id.summaryLabel)
        mTimeView = findViewById(R.id.timeLabel)
        mIconView = findViewById(R.id.iconImageView)
        mRefreshView = findViewById<ImageView>(R.id.refreshImageView).apply {
            setOnClickListener {
                viewModel.getForecast(true)
            }
        }
        mProgressBar = findViewById(R.id.progressBar)
        hourlyButton = findViewById<Button>(R.id.hourlyButton).apply {
            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    R.string.no_data_yet_message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        dailyButton = findViewById<Button>(R.id.dailyButton).apply {
            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    R.string.no_data_yet_message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.state.collect { state ->
                mProgressBar.isVisible = state.isLoading
                mRefreshView.isVisible = !state.isLoading

                state.error?.let {
                    alertUserAboutError()
                }

                val forecast = state.forecast ?: return@collect

                mTemperatureView.text = forecast.current.temperature.toString()
                mTimeView.text = resources.getString(
                    R.string.time_label_text,
                    forecast.current.getFormattedTime(forecast.timezone),
                )
                mHumidityView.text = "${forecast.current.humidity}%"
                mPrecipView.text = "${forecast.current.precipitationProbability}%"
                mSummaryView.text = forecast.current.summary
                mIconView.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        forecast.current.type.toIconResId(),
                        null
                    )
                )

                hourlyButton.setOnClickListener {
                    startHourlyActivity(forecast.timezone, forecast.hourlyForecast.records)
                }
                dailyButton.setOnClickListener {
                    startDailyActivity(forecast.timezone, forecast.dailyForecast.records)
                }
            }
        }

        viewModel.getForecast(false)
    }

    private fun startDailyActivity(
        timezone: String,
        dailyForecast: List<ForecastRecord>,
    ) {
        startActivity(
            Intent(this, DailyForecastActivity::class.java).apply {
                putExtra(DAILY_FORECAST, DailyForecastArgs(timezone, dailyForecast))
            }
        )
    }

    private fun startHourlyActivity(
        timezone: String,
        hourlyForecast: List<ForecastRecord>,
    ) {
        startActivity(
            Intent(this, HourlyForecastActivity::class.java).apply {
                putExtra(HOURLY_FORECAST, HourlyForecastArgs(timezone, hourlyForecast))
            }
        )
    }

    private fun alertUserAboutError() {
        val alertDialogFragment = AlertDialogFragment()
        alertDialogFragment.show(fragmentManager, "error_dialog")
    }

}
