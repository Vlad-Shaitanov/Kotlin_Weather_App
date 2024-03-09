package com.example.weather_application.fragments

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

//Функция для запроса разрешений от пользователя
fun Fragment.isPermissionsGranted(p: String): Boolean {

    /*Метод checkSelfPermission() возвращает число (-1, 0) и его мы сравниваем с константой*/
    return ContextCompat.checkSelfPermission(activity as AppCompatActivity, p) == PackageManager.PERMISSION_GRANTED
}