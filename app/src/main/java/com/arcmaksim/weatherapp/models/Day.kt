package com.arcmaksim.weatherapp.models

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*

class Day() : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Day> = object : Parcelable.Creator<Day> {
            override fun createFromParcel(p0: Parcel?): Day {
                return Day(p0)
            }

            override fun newArray(size: Int): Array<out Day> {
                return Array(size) {Day()}
            }

        }
    }

    var mTime: Long = 0
    lateinit var mSummary: String
    var mTemperatureMax: Double = 0.0
    lateinit var mIconId: String
    lateinit var mTimezone: String

    fun getTemperature(): Int {
        return Forecast.convertFahrenheitToCelcius(Math.round(mTemperatureMax).toInt())
    }

    fun getIconId(): Int {
        return Forecast.getIconId(mIconId)
    }

    fun getDayOfTheWeek(): String {
        val formatter: SimpleDateFormat = SimpleDateFormat("EEEE")
        formatter.timeZone = TimeZone.getTimeZone(mTimezone)
        val timeString: String = formatter.format(mTime * 1000)
        return timeString
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeLong(mTime)
        p0?.writeString(mSummary)
        p0?.writeDouble(mTemperatureMax)
        p0?.writeString(mIconId)
        p0?.writeString(mTimezone)
    }

    override fun describeContents(): Int {
        return 0;
    }

    private constructor(parcel: Parcel?): this() {
        if (parcel != null) {
            mTime = parcel.readLong()
            mSummary = parcel.readString()
            mTemperatureMax = parcel.readDouble()
            mIconId = parcel.readString()
            mTime = parcel.readLong()
        }
    }

}