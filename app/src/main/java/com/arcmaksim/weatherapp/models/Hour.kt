package com.arcmaksim.weatherapp.models

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*

class Hour() : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Hour> = object : Parcelable.Creator<Hour> {
            override fun createFromParcel(p0: Parcel?): Hour {
                return Hour(p0)
            }

            override fun newArray(size: Int): Array<out Hour> {
                return Array(size) {Hour()}
            }

        }
    }

    var mTime: Long = 0
    lateinit var mSummary: String
    var mTemperature: Double = 0.0
    lateinit var mIconId: String
    lateinit var mTimezone: String

    fun getTemperature(): Int {
        return convertFahrenheitToCelcius(Math.round(mTemperature).toInt())
    }

    fun convertFahrenheitToCelcius(temperatureInFahrenheit: Int) : Int {
        return ((temperatureInFahrenheit - 32) * 5 / 9)
    }

    fun getFormattedTime(): String {
        val formatter = SimpleDateFormat("H")
        formatter.timeZone = TimeZone.getTimeZone(mTimezone)
        val stringBuilder = StringBuilder(formatter.format(mTime * 1000))
                .append(":00")
        return stringBuilder.toString()
    }

    fun getIconId(): Int {
        return Forecast.getIconId(mIconId)
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeLong(mTime)
        p0?.writeString(mSummary)
        p0?.writeDouble(mTemperature)
        p0?.writeString(mIconId)
        p0?.writeString(mTimezone)
    }

    override fun describeContents(): Int {
        return 0
    }

    private constructor(parcel: Parcel?): this() {
        if (parcel != null) {
            mTime = parcel.readLong()
            mSummary = parcel.readString()
            mTemperature = parcel.readDouble()
            mIconId = parcel.readString()
            mTimezone = parcel.readString()
        }
    }
}