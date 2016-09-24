package com.arcmaksim.weatherapp.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import com.arcmaksim.weatherapp.R

class AlertDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context: Context = activity
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
                .setTitle(R.string.error_title)
                .setMessage(R.string.error_message)
                .setPositiveButton(R.string.error_ok_button_text, null)
        return alertDialogBuilder.create()
    }

}