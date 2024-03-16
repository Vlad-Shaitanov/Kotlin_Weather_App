package com.example.weather_application

import android.app.AlertDialog
import android.content.Context

object DialogManager {
    fun locationSettingsDialog(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)

        //Создаем и настраиваем диалог
        val dialog = builder.create()
        dialog.setTitle("Enable GPS?")
        dialog.setMessage("GPS is disabled. Do you want to turn it on?")

        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            listener.onClick()
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { _, _ ->
            dialog.dismiss()
        }

        dialog.show()
    }

    //Своеобразный мост между диалогом и MainFragment
    interface Listener {
        fun onClick()
    }
}