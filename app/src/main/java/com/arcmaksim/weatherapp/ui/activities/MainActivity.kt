package com.arcmaksim.weatherapp.ui.activities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.arcmaksim.weatherapp.R
import com.arcmaksim.weatherapp.ui.fragments.AlertDialogFragment
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
                        Log.v(TAG, response?.body()?.string())
                        if (!response?.isSuccessful!!) {
                            alertUserAboutError()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

            })

        } else {
            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_SHORT).show()
        }

    }

    private fun isNetworkAvailable(): Boolean {
        val manager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = manager.activeNetworkInfo
        var isAvailable: Boolean = false
        if (networkInfo != null) {
            isAvailable = true
        }
        return isAvailable
    }

    private fun alertUserAboutError() {
        val alertDialogFragment: AlertDialogFragment = AlertDialogFragment()
        alertDialogFragment.show(fragmentManager, "error_dialog")
    }
}
