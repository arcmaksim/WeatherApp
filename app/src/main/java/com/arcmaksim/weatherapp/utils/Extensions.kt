package com.arcmaksim.weatherapp.utils


import android.app.Activity
import android.content.Intent


/**
 * Created by Arcmaksim on 10/30/2017.
 */
inline fun <reified T : Activity> Activity.navigate(tag: String, data: Array<*>?) {
	val intent = Intent(this, T::class.java).apply {
		putExtra(tag, data)
	}
	startActivity(intent)
}