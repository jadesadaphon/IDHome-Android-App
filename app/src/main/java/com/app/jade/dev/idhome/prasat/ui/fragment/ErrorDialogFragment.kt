package com.app.jade.dev.idhome.prasat.ui.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ErrorDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Start game")
                .setPositiveButton("Start") { dialog, id ->

                }
                .setNegativeButton("Cancel") { dialog, id ->

                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}