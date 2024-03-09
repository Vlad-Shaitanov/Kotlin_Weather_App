package com.example.weather_application

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    //Здесь будет прогноз погоды для выбранного времени
    val liveDataCurrent = MutableLiveData<String>()

    //Здесь будет прогноз погоды для списка
    val liveDataList = MutableLiveData<String>()
}