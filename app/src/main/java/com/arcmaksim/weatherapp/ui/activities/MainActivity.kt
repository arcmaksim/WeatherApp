package com.arcmaksim.weatherapp.ui.activities

import android.app.Activity
import androidx.activity.ComponentActivity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import com.arcmaksim.weatherapp.R
import com.arcmaksim.weatherapp.models.Current
import com.arcmaksim.weatherapp.models.Day
import com.arcmaksim.weatherapp.models.Forecast
import com.arcmaksim.weatherapp.models.Hour
import com.arcmaksim.weatherapp.ui.DailyForecastArgs
import com.arcmaksim.weatherapp.ui.HourlyForecastArgs
import com.arcmaksim.weatherapp.ui.fragments.AlertDialogFragment
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : ComponentActivity(R.layout.activity_main) {

    companion object {
        @JvmStatic
        val TAG = MainActivity::class.java.simpleName

        @JvmStatic
        val DAILY_FORECAST = "DAILY_FORECAST"

        @JvmStatic
        val HOURLY_FORECAST = "HOURLY_FORECAST"
    }

    var mForecast: Forecast? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mTemperatureView = findViewById(R.id.temperatureLabel)
        mHumidityView = findViewById(R.id.humidityValue)
        mPrecipView = findViewById(R.id.precipValue)
        mSummaryView = findViewById(R.id.summaryLabel)
        mTimeView = findViewById(R.id.timeLabel)
        mIconView = findViewById(R.id.iconImageView)
        mRefreshView = findViewById<ImageView>(R.id.refreshImageView).apply {
            setOnClickListener {
                getForecast()
            }
        }
        mProgressBar = findViewById(R.id.progressBar)
        hourlyButton = findViewById<Button>(R.id.hourlyButton).apply {
            setOnClickListener {
                startHourlyActivity()
            }
        }
        dailyButton = findViewById<Button>(R.id.dailyButton).apply {
            setOnClickListener {
                startDailyActivity()
            }
        }

        getForecast()
    }

    fun getForecast() {
        val apiKey = resources.getString(R.string.api_key)
        val latitude = resources.getString(R.string.default_address_latitude)
        val longitude = resources.getString(R.string.default_address_longitude)
        var url = resources.getString(R.string.url)
        url = String.format(url, apiKey, latitude, longitude)

        if (isNetworkAvailable()) {
            toggleRefresh()

            val okHttpClient = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            val call = okHttpClient.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    runOnUiThread { toggleRefresh() }
                }

                override fun onResponse(call: Call?, response: Response?) {
                    runOnUiThread { toggleRefresh() }
                    try {
                        val jsonData = response?.body()?.string()
                        Log.v(TAG, jsonData!!)
                        if (response?.isSuccessful!!) {
                            mForecast = parseForecastDetails(jsonData)
                            runOnUiThread { updateDisplay() }
                        } else {
                            alertUserAboutError()
                        }
                    } catch (e: IOException) {
                        Log.e(TAG, "Exception caught:", e)
                    } catch (e: JSONException) {
                        Log.e(TAG, "Exception caught:", e)
                    }
                }

            })

        } else {
            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleRefresh() {
        if (mProgressBar.visibility == View.INVISIBLE) {
            mProgressBar.visibility = View.VISIBLE
            mRefreshView.visibility = View.INVISIBLE
        } else {
            mProgressBar.visibility = View.INVISIBLE
            mRefreshView.visibility = View.VISIBLE
        }
    }

    private fun updateDisplay() {
        mTemperatureView.text = mForecast!!.mCurrent.getTemperature().toString()
        val stringBuilder: StringBuilder = StringBuilder()
        var timeLabelString: String = resources.getString(R.string.time_label_text)
        timeLabelString = String.format(timeLabelString, mForecast!!.mCurrent.getFormattedTime())
        mTimeView.text = timeLabelString
        stringBuilder.append(mForecast!!.mCurrent.getHumidity())
            .append("%")
        mHumidityView.text = stringBuilder
        stringBuilder.setLength(0)
        stringBuilder.append(mForecast!!.mCurrent.getPrecipChance())
            .append("%")
        mPrecipView.text = stringBuilder
        mSummaryView.text = mForecast!!.mCurrent.mSummary
        mIconView.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                mForecast!!.mCurrent.getIconId(),
                null
            )
        )
    }

    @Throws(JSONException::class)
    private fun getWeatherDetails(jsonData: String?): Current {
        val forecast = JSONObject(jsonData)
        val timezone = forecast.getString("timezone")

        val currently = forecast.getJSONObject("currently")
        val current = Current()
        current.mTime = currently.getLong("time")
        current.mTemperature = currently.getDouble("temperature")
        current.mIcon = currently.getString("icon")
        current.mHumidity = currently.getDouble("humidity")
        current.mPrecipChance = currently.getDouble("precipProbability")
        current.mSummary = currently.getString("summary")
        current.mTimezone = timezone

        return current
    }

    @Throws(JSONException::class)
    private fun parseForecastDetails(jsonData: String?): Forecast {
        val forecast = Forecast()
        forecast.mCurrent = getWeatherDetails(jsonData)
        forecast.mHourlyForecast = getHourlyDetails(jsonData)
        forecast.mDailyForecast = getDailyForecast(jsonData)
        return forecast
    }

    inline fun <reified T : Activity> Activity.navigate(tag: String, data: Array<*>?) {
        val intent = Intent(this, T::class.java)
        intent.putExtra(tag, data)
        startActivity(intent)
    }

    private fun startDailyActivity() {
        val dailyForecast = mForecast?.mDailyForecast ?: let {
            Toast.makeText(
                this,
                R.string.no_data_yet_message,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        startActivity(
            Intent(this, DailyForecastActivity::class.java).apply {
                putExtra(DAILY_FORECAST, DailyForecastArgs(dailyForecast.toList()))
            }
        )
    }

    private fun startHourlyActivity() {
        val hourlyForecast = mForecast?.mHourlyForecast ?: let {
            Toast.makeText(
                this,
                R.string.no_data_yet_message,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        startActivity(
            Intent(this, HourlyForecastActivity::class.java).apply {
                putExtra(HOURLY_FORECAST, HourlyForecastArgs(hourlyForecast.toList()))
            }
        )
    }

    private fun getDailyForecast(jsonData: String?): Array<Day> {
        val forecast = JSONObject(jsonData)
        val timezone = forecast.getString("timezone")
        val data = forecast.getJSONObject("daily").getJSONArray("data")
        return Array(data.length()) { index ->
            val record = data.getJSONObject(index)
            Day(
                iconId = Forecast.resolveIconId(record.getString("icon")),
                summary = record.getString("summary"),
                temperature = Forecast.convertFahrenheitToCelsius(
                    record.getString("temperatureMax").toDouble().toInt()
                ),
                timezone = timezone,
                time = record.getString("time").toLong(),
            )
        }
    }

    private fun getHourlyDetails(jsonData: String?): Array<Hour> {
        val forecast = JSONObject(jsonData)
        val timezone = forecast.getString("timezone")
        val data = forecast.getJSONObject("hourly").getJSONArray("data")

        return Array(data.length()) { index ->
            val record = data.getJSONObject(index)
            Hour(
                iconId = Forecast.resolveIconId(record.getString("icon")),
                summary = record.getString("summary"),
                temperature = Forecast.convertFahrenheitToCelsius(
                    record.getString("temperature").toDouble().toInt()
                ),
                time = record.getString("time").toLong(),
                timezone = timezone,
            )
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        return networkInfo?.isConnected ?: false
    }

    private fun alertUserAboutError() {
        val alertDialogFragment = AlertDialogFragment()
        alertDialogFragment.show(fragmentManager, "error_dialog")
    }
}
