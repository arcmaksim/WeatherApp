package com.arcmaksim.weatherapp.models


import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*


class Day() : Parcelable {

	companion object {

		@JvmField
		val CREATOR: Parcelable.Creator<Day> = object : Parcelable.Creator<Day> {

			override fun createFromParcel(p0: Parcel?) = Day(p0)


			override fun newArray(size: Int) = Array(size) { Day() }

		}

	}

	var time: Long = 0
	var summary = ""
	var temperatureMax: Double = 0.0
	var iconId = ""
	var timezone = ""


	private constructor(parcel: Parcel?) : this() {
		parcel?.let {
			it.readLong()
			it.readString()
			it.readDouble()
			it.readString()
			it.readString()
		}
	}


	fun getTemperature() = Forecast.convertFahrenheitToCelsius(Math.round(temperatureMax).toInt())


	fun getIconId() = Forecast.getIconId(iconId)


	fun getDayOfTheWeek(): String {
		val formatter = SimpleDateFormat("EEEE")
		formatter.timeZone = TimeZone.getTimeZone(timezone)
		return formatter.format(time * 1000)
	}


	override fun writeToParcel(p0: Parcel?, p1: Int) {
		p0?.writeLong(time)
		p0?.writeString(summary)
		p0?.writeDouble(temperatureMax)
		p0?.writeString(iconId)
		p0?.writeString(timezone)
	}


	override fun describeContents() = 0

}