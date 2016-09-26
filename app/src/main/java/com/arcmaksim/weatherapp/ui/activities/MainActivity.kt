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
import com.arcmaksim.weatherapp.model.CurrentWeather
import com.arcmaksim.weatherapp.ui.fragments.AlertDialogFragment
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val TAG: String = "MainActivity"
    lateinit var mCurrentWeather: CurrentWeather

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
                            mCurrentWeather = getWeatherDetails(jsonData)
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
        mTemperatureView.text = mCurrentWeather.getTemperature().toString()
        val stringBuilder: StringBuilder = StringBuilder()
        var timeLabelString: String = resources.getString(R.string.timeLabelText)
        timeLabelString = String.format(timeLabelString, mCurrentWeather.getFormattedTime())
        mTimeView.text = timeLabelString
        mHumidityView.text = mCurrentWeather.mHumidity.toString()
        stringBuilder.append(mCurrentWeather.getPrecipChance())
                .append("%")
        mPrecipView.text = stringBuilder
        mSummaryView.text = mCurrentWeather.mSummary
        mIconView.setImageDrawable(ResourcesCompat.getDrawable(resources, mCurrentWeather.getIconId(), null))
    }

    @Throws(JSONException::class)
    private fun getWeatherDetails(jsonData: String?): CurrentWeather {
        val forecast: JSONObject = JSONObject(jsonData)
        val timezone: String = forecast.getString("timezone")

        val currently: JSONObject = forecast.getJSONObject("currently")
        val currentWeather: CurrentWeather = CurrentWeather()
        currentWeather.mTime = currently.getLong("time")
        currentWeather.mTemperature = currently.getDouble("temperature")
        currentWeather.mIcon = currently.getString("icon")
        currentWeather.mHumidity = currently.getDouble("humidity")
        currentWeather.mPrecipChance = currently.getDouble("precipProbability")
        currentWeather.mSummary = currently.getString("summary")
        currentWeather.mTimezone = timezone

        return currentWeather
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
