package com.arcmaksim.weatherapp.ui.activities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.arcmaksim.weatherapp.model.CurrentWeather
import com.arcmaksim.weatherapp.R
import com.arcmaksim.weatherapp.ui.fragments.AlertDialogFragment
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val TAG: String = "MainActivity"
    var mCurrentWeather: CurrentWeather? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiKey: String = "0477e00681642c3961f2e5621998ce85"
        val latitude: Double = 37.8267
        val longitude: Double = -122.4233
        val stringBuilder: StringBuilder = StringBuilder()

        stringBuilder.append("https://api.darksky.net/forecast/")
                .append(apiKey)
                .append("/")
                .append(latitude)
                .append(",")
                .append(longitude)

        val url: String = stringBuilder.toString()

        if (isNetworkAvailable()) {

            val okHttpClient: OkHttpClient = OkHttpClient()
            val request: Request = Request.Builder()
                    .url(url)
                    .build()

            val call: Call = okHttpClient.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                }

                override fun onResponse(call: Call?, response: Response?) {
                    try {
                        val jsonData: String? = response?.body()?.string()
                        Log.v(TAG, jsonData)
                        if (response?.isSuccessful!!) {
                            mCurrentWeather = getWeatherDetails(jsonData)
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

    @Throws(JSONException::class)
    private fun getWeatherDetails(jsonData: String?): CurrentWeather {
        val forecast: JSONObject = JSONObject(jsonData)
        val timezone: String = forecast.getString("timezone")
        Log.i(TAG, "From JSON" + timezone)

        val currently: JSONObject = forecast.getJSONObject("currently")
        val currentWeather: CurrentWeather = CurrentWeather(currently.getLong("time"))
        currentWeather.mTemp = currently.getDouble("temperature")
        currentWeather.mIcon = currently.getString("icon")
        currentWeather.mHumidity = currently.getDouble("humidity")
        currentWeather.mPrecipChance = currently.getDouble("precipProbability")
        currentWeather.mSummary = currently.getString("summary")
        currentWeather.mTimezone = timezone
        Log.i("Current weather time", currentWeather.getFormattedTime())

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
