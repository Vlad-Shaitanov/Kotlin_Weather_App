package com.example.weather_application

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText

object DialogManager {
    fun locationSettingsDialog(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)

        //Создаем и настраиваем диалог
        val dialog = builder.create()
        dialog.setTitle("Enable GPS?")
        dialog.setMessage("GPS is disabled. Do you want to turn it on?")

        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            listener.onClick(null)
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { _, _ ->
            dialog.dismiss()
        }

        dialog.show()
    }

    fun searchByNameDialog(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        val editText = EditText(context)

        builder.setView(editText) //Добавили в диалог поле с текстом

        //Создаем и настраиваем диалог
        val dialog = builder.create()
        dialog.setTitle("City name:")

        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            listener.onClick(editText.text.toString())
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { _, _ ->
            dialog.dismiss()
        }

        dialog.show()
    }

    //Своеобразный мост между диалогом и MainFragment
    interface Listener {
        fun onClick(name: String?)
    }
}