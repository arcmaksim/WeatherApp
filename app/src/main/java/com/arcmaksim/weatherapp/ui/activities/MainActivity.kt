package com.arcmaksim.weatherapp.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.arcmaksim.weatherapp.R
import com.arcmaksim.weatherapp.models.Current
import com.arcmaksim.weatherapp.models.Day
import com.arcmaksim.weatherapp.models.Forecast
import com.arcmaksim.weatherapp.models.Hour
import com.arcmaksim.weatherapp.ui.fragments.AlertDialogFragment
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        val TAG = MainActivity::class.java.simpleName
        @JvmStatic
        val DAILY_FORECAST = "DAILY_FORECAST"
        @JvmStatic
        val HOURLY_FORECAST = "HOURLY_FORECAST"
    }

    var mForecast: Forecast? = null

    @BindView(R.id.temperatureLabel) lateinit var mTemperatureView: TextView
    @BindView(R.id.humidityValue) lateinit var mHumidityView: TextView
    @BindView(R.id.precipValue) lateinit var mPrecipView: TextView
    @BindView(R.id.summaryLabel) lateinit var mSummaryView: TextView
    @BindView(R.id.timeLabel) lateinit var mTimeView: TextView
    @BindView(R.id.iconImageView) lateinit var mIconView: ImageView
    @BindView(R.id.refreshImageView) lateinit var mRefreshView: ImageView
    @BindView(R.id.progressBar) lateinit var mProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        getForecast()
    }

    @OnClick(R.id.refreshImageView)
    fun updateForecast() {
        getForecast()
    }

    fun getForecast() {
        val apiKey= resources.getString(R.string.api_key)
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
                        Log.v(TAG, jsonData)
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
        mIconView.setImageDrawable(ResourcesCompat.getDrawable(resources, mForecast!!.mCurrent.getIconId(), null))
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

    @OnClick(R.id.dailyButton)
    fun startDailyActivity() {
        if (mForecast != null) {
            navigate<DailyForecastActivity>(DAILY_FORECAST, mForecast?.mDailyForecast as Array<*>?)
        } else {
            Toast.makeText(this, R.string.no_data_yet_message, Toast.LENGTH_SHORT).show()
        }
    }

    @OnClick(R.id.hourlyButton)
    fun startHourlyActivity() {
        if (mForecast != null) {
            navigate<HourlyForecastActivity>(HOURLY_FORECAST, mForecast?.mHourlyForecast as Array<*>?)
        } else {
            Toast.makeText(this, R.string.no_data_yet_message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDailyForecast(jsonData: String?): Array<Day> {
        val forecast = JSONObject(jsonData)
        val timezone = forecast.getString("timezone")
        val data = forecast.getJSONObject("daily").getJSONArray("data")
        val dailyForecast = Array(data.length()) { Day() }

        for (i in 0..data.length() - 1) {
            val record = data.getJSONObject(i)
            dailyForecast[i].mSummary = record.getString("summary")
            dailyForecast[i].mTemperatureMax = record.getString("temperatureMax").toDouble()
            dailyForecast[i].mIconId = record.getString("icon")
            dailyForecast[i].mTimezone = timezone
            dailyForecast[i].mTime = record.getString("time").toLong()
        }

        return dailyForecast
    }

    private fun getHourlyDetails(jsonData: String?): Array<Hour> {
        val forecast = JSONObject(jsonData)
        val timezone = forecast.getString("timezone")
        val data = forecast.getJSONObject("hourly").getJSONArray("data")
        val hourlyForecast = Array(data.length()) { Hour() }

        for (i in 0..data.length() - 1) {
            val record = data.getJSONObject(i)
            hourlyForecast[i].mSummary = record.getString("summary")
            hourlyForecast[i].mTemperature = record.getString("temperature").toDouble()
            hourlyForecast[i].mTimezone = timezone
            hourlyForecast[i].mIconId = record.getString("icon")
            hourlyForecast[i].mTime = record.getString("time").toLong()
        }

        return hourlyForecast
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
