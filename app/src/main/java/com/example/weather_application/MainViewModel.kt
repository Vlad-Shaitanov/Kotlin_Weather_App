package com.example.weather_application

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_application.adapters.WeatherModel

class MainViewModel: ViewModel() {
    //Здесь будет прогноз погоды для выбранного времени
    val liveDataCurrent = MutableLiveData<WeatherModel>()

    //Здесь будет прогноз погоды для списка
    val liveDataList = MutableLiveData<List<WeatherModel>>()
}