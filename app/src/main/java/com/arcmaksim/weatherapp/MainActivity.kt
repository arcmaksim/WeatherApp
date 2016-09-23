package com.arcmaksim.weatherapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val TAG: String = "MainActivity"

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

        val okHttpClient: OkHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
                .url(url)
                .build()

        val call: Call = okHttpClient.newCall(request)
        call.enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {}

            override fun onResponse(call: Call?, response: Response?) {
                try {
                    Log.v(TAG, response?.body()?.string())
                    if (!response?.isSuccessful!!) {
                        alertUserAboutError()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

        })

    }

    private fun alertUserAboutError() {

    }
}
