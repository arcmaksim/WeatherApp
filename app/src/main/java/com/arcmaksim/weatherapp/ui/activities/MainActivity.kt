package com.arcmaksim.weatherapp.ui.activities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
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
import com.arcmaksim.weatherapp.model.Current
import com.arcmaksim.weatherapp.model.Day
import com.arcmaksim.weatherapp.model.Forecast
import com.arcmaksim.weatherapp.model.Hour
import com.arcmaksim.weatherapp.ui.fragments.AlertDialogFragment
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val TAG: String = "MainActivity"
    lateinit var mForecast: Forecast

    @BindView(R.id.temperatureLabel)
    lateinit var mTemperatureView: TextView

    @BindView(R.id.humidityValue)
    lateinit var mHumidityView: TextView

    @BindView(R.id.precipValue)
    lateinit var mPrecipView: TextView

    @BindView(R.id.summaryLabel)
    lateinit var mSummaryView: TextView

    @BindView(R.id.timeLabel)
    lateinit var mTimeView: TextView

    @BindView(R.id.iconImageView)
    lateinit var mIconView: ImageView

    @BindView(R.id.refreshImageView)
    lateinit var mRefreshView: ImageView

    @BindView(R.id.progressBar)
    lateinit var mProgressBar: ProgressBar

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
        val apiKey: String = resources.getString(R.string.apiKey)
        val latitude: String = resources.getString(R.string.defaultAddressLatitude)
        val longitude: String = resources.getString(R.string.defaultAddressLongitude)
        var url: String = resources.getString(R.string.url)
        url = String.format(url, apiKey, latitude, longitude)

        if (isNetworkAvailable()) {
            toggleRefresh()

            val okHttpClient: OkHttpClient = OkHttpClient()
            val request: Request = Request.Builder()
                    .url(url)
                    .build()

            val call: Call = okHttpClient.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    runOnUiThread { toggleRefresh()}
                }

                override fun onResponse(call: Call?, response: Response?) {
                    runOnUiThread { toggleRefresh()}
                    try {
                        val jsonData: String? = response?.body()?.string()
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
        mTemperatureView.text = mForecast.mCurrent.getTemperature().toString()
        val stringBuilder: StringBuilder = StringBuilder()
        var timeLabelString: String = resources.getString(R.string.timeLabelText)
        timeLabelString = String.format(timeLabelString, mForecast.mCurrent.getFormattedTime())
        mTimeView.text = timeLabelString
        mHumidityView.text = mForecast.mCurrent.mHumidity.toString()
        stringBuilder.append(mForecast.mCurrent.getPrecipChance())
                .append("%")
        mPrecipView.text = stringBuilder
        mSummaryView.text = mForecast.mCurrent.mSummary
        mIconView.setImageDrawable(ResourcesCompat.getDrawable(resources, mForecast.mCurrent.getIconId(), null))
    }

    @Throws(JSONException::class)
    private fun getWeatherDetails(jsonData: String?): Current {
        val forecast: JSONObject = JSONObject(jsonData)
        val timezone: String = forecast.getString("timezone")

        val currently: JSONObject = forecast.getJSONObject("currently")
        val current: Current = Current()
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
        val forecast: Forecast = Forecast()
        mForecast.mCurrent = getWeatherDetails(jsonData)
        mForecast.mHourlyForecast = getHourlyDetails(jsonData)
        mForecast.mDailyForecast = getDailyForecast(jsonData)
        return forecast
    }

    private fun getDailyForecast(jsonData: String?): Array<Day> {
        val forecast: JSONObject = JSONObject(jsonData)
        val timezone: String = forecast.getString("timezone")
        val data: JSONArray = forecast.getJSONObject("daily").getJSONArray("data")
        var dailyForecast: Array<Day> = Array(data.length()) {Day()}

        for (i in 0..data.length()) {
            val record: JSONObject = data.getJSONObject(i)
            dailyForecast[i].mSummary = record.getString("summary")
            dailyForecast[i].mTemperatureMax = record.getString("temperatureMax").toDouble()
            dailyForecast[i].mIconId = record.getString("icon")
            dailyForecast[i].mTimezone = timezone
            dailyForecast[i].mTime = record.getString("time").toLong()
        }

        return dailyForecast
    }

    private fun getHourlyDetails(jsonData: String?): Array<Hour> {
        val forecast: JSONObject = JSONObject(jsonData)
        val timezone: String = forecast.getString("timezone")
        val data: JSONArray = forecast.getJSONObject("hourly").getJSONArray("data")
        var hourlyForecast: Array<Hour> = Array(data.length()) {Hour()}

        for (i in 0..data.length()) {
            val record: JSONObject = data.getJSONObject(i)
            hourlyForecast[i].mSummary = record.getString("summary")
            hourlyForecast[i].mTemperature = record.getString("temperature").toDouble()
            hourlyForecast[i].mTimezone = timezone
            hourlyForecast[i].mIconId = record.getString("icon")
            hourlyForecast[i].mTime = record.getString("time").toLong()
        }
        return hourlyForecast
    }

    private fun isNetworkAvailable(): Boolean {
        val manager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = manager.activeNetworkInfo
        val isAvailable: Boolean = networkInfo?.isConnected ?: false
        return isAvailable
    }

    private fun alertUserAboutError() {
        val alertDialogFragment: AlertDialogFragment = AlertDialogFragment()
        alertDialogFragment.show(fragmentManager, "error_dialog")
    }
}
