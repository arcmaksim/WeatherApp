package com.arcmaksim.weatherapp.ui.activities


import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.arcmaksim.weatherapp.models.Current
import com.arcmaksim.weatherapp.models.Day
import com.arcmaksim.weatherapp.models.Forecast
import com.arcmaksim.weatherapp.models.Hour
import com.arcmaksim.weatherapp.ui.fragments.AlertDialogFragment
import com.arcmaksim.weatherapp.utils.navigate
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {

	companion object {

		val TAG: String = MainActivity::class.java.simpleName
		const val DAILY_FORECAST = "DAILY_FORECAST"
		const val HOURLY_FORECAST = "HOURLY_FORECAST"

	}

	var forecast: Forecast? = null


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		dailyButton?.setOnClickListener {
			forecast?.let {
				navigate<DailyForecastActivity>(DAILY_FORECAST, it.mDailyForecast as Array<*>?)
			} ?: toast(R.string.no_data_yet_message)
		}

		hourlyButton?.setOnClickListener {
			forecast?.let {
				navigate<HourlyForecastActivity>(HOURLY_FORECAST, it.mHourlyForecast as Array<*>?)
			} ?: toast(R.string.no_data_yet_message)
		}

		refreshImageView?.setOnClickListener { getForecast() }

		getForecast()
	}


	private fun getForecast() {
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

				override fun onFailure(call: Call?, e: IOException?) =
						runOnUiThread { toggleRefresh() }


				override fun onResponse(call: Call?, response: Response?) {
					runOnUiThread { toggleRefresh() }
					try {
						val jsonData = response?.body()?.string()
						Log.v(TAG, jsonData)
						if (response?.isSuccessful!!) {
							forecast = parseForecastDetails(jsonData)
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
			toast(R.string.network_unavailable_message)
		}
	}


	private fun toggleRefresh() {
		if (progressBar.visible) {
			progressBar.hide(false)
			refreshImageView.show()
		} else {
			refreshImageView.hide(false)
			progressBar.show()
		}
	}


	@SuppressLint("SetTextI18n")
	private fun updateDisplay() {
		temperatureLabel.text = forecast!!.mCurrent.getTemperature().toString()

		var timeLabelString: String = resources.getString(R.string.time_label_text)
		timeLabelString = String.format(timeLabelString, forecast!!.mCurrent.getFormattedTime())
		timeLabel.text = timeLabelString

		humidityValue.text = "${forecast!!.mCurrent.getHumidity()}%"
		precipValue?.text = "${forecast!!.mCurrent.getPrecipChance()}%"
		summaryLabel?.text = forecast!!.mCurrent.mSummary

		iconImageView?.setImageDrawable(ResourcesCompat.getDrawable(resources,
				forecast!!.mCurrent.getIconId(),
				null))
	}


	@Throws(JSONException::class)
	private fun getWeatherDetails(jsonData: String?): Current {
		val forecast = JSONObject(jsonData)
		val timezone = forecast.getString("timezone")

		val currently = forecast.getJSONObject("currently")
		return Current().apply {
			mTime = currently.getLong("time")
			mTemperature = currently.getDouble("temperature")
			mIcon = currently.getString("icon")
			mHumidity = currently.getDouble("humidity")
			mPrecipChance = currently.getDouble("precipProbability")
			mSummary = currently.getString("summary")
			mTimezone = timezone
		}
	}


	@Throws(JSONException::class)
	private fun parseForecastDetails(jsonData: String?) =
			Forecast().apply {
				mCurrent = getWeatherDetails(jsonData)
				mHourlyForecast = getHourlyDetails(jsonData)
				mDailyForecast = getDailyForecast(jsonData)
			}


	private fun getDailyForecast(jsonData: String?): Array<Day> {
		val forecast = JSONObject(jsonData)
		val timezone = forecast.getString("timezone")
		val data = forecast.getJSONObject("daily").getJSONArray("data")

		return Array(data.length()) { i ->
			val record = data.getJSONObject(i)
			Day().apply {
				summary = record.getString("summary")
				temperatureMax = record.getString("temperatureMax").toDouble()
				iconId = record.getString("icon")
				this.timezone = timezone
				time = record.getString("time").toLong()
			}
		}
	}


	private fun getHourlyDetails(jsonData: String?): Array<Hour> {
		val forecast = JSONObject(jsonData)
		val timezone = forecast.getString("timezone")
		val data = forecast.getJSONObject("hourly").getJSONArray("data")

		return Array(data.length()) { i ->
			val record = data.getJSONObject(i)
			Hour().apply {
				mSummary = record.getString("summary")
				mTemperature = record.getString("temperature").toDouble()
				mTimezone = timezone
				mIconId = record.getString("icon")
				mTime = record.getString("time").toLong()
			}
		}
	}


	private fun isNetworkAvailable(): Boolean {
		val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		val networkInfo = manager.activeNetworkInfo
		return networkInfo?.isConnected ?: false
	}


	private fun alertUserAboutError() =
			AlertDialogFragment().apply {
				show(fragmentManager, "error_dialog")
			}

}