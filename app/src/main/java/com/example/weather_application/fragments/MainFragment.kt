package com.example.weather_application.fragments

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather_application.BuildConfig
import com.example.weather_application.MainViewModel
import com.example.weather_application.R
import com.example.weather_application.adapters.VpAdapter
import com.example.weather_application.adapters.WeatherModel
import com.example.weather_application.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import org.json.JSONObject

class MainFragment : Fragment() {
    private val fragmentsList = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )
    private val layoutTabsList = listOf(
        R.string.hours,
        R.string.days
    )
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentMainBinding
    private val apiKey = BuildConfig.API_KEY_WEATHERAPI //Ключ от Weather Api
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Пример ViewBinding во фрагментах
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Когда все вью уже созданы
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        initViewAdapter()
        updateCurrentCard() // Ждем обновлений данных liveData
        getWeatherData("Moscow")
    }

    private fun initViewAdapter() = with(binding) {
        //Инициализация адаптера для Вью пейджера (передаем активити и список активити для переключения)
        val adapter = VpAdapter(activity as FragmentActivity, fragmentsList)

        //Подключения адаптера к Вью пейджеру
        vp.adapter = adapter

        // tabLayout и vp это id самого ТабЛейаута и вьюпейджера из верстки фрагмента
        TabLayoutMediator(tabLayout, vp) { tab, position ->
            //Указываем названия для табов
            tab.text = getString(layoutTabsList[position])
        }.attach()
    }

    private fun updateCurrentCard() = with(binding) {
        model.liveDataCurrent.observe(viewLifecycleOwner) {
            val maxMinTemp = "${it.maxTemp}°C / ${it.minTemp}°C"

            tvData.text = it.time
            tvCity.text = it.city
            tvCurrentTemp.text = it.currentTemp.ifEmpty {
                maxMinTemp
            }
            tvCondition.text = it.condition
            tvMaxMin.text = if (it.currentTemp.isEmpty()) "" else maxMinTemp

            //Указываем библиотеке Picasso загрузить картинку в нужное место
            Picasso.get()
                .load("https:${it.imageUrl}")
                .into(ivWeather)
        }
    }

    private fun permissionListener() {
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
        }
    }

    //Проверяем, есть ли уже разрешение
    private fun checkPermission() {
        // Передается Manifest(android)
        if (!isPermissionsGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            //Если разрешения нет, то регистрируем листенер и спрашиваем разрешение у пользователя
            permissionListener()

            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getWeatherData(city: String) {
        val url =
            "https://api.weatherapi.com/v1/forecast.json?key=$apiKey&q=$city&days=3&aqi=no&alerts=no"

        //Создали очередь для запросов Volley
        val queue = Volley.newRequestQueue(context)
        //Настройка запроса
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                parseWeatherData(response)
            },
            {
                //Слушатель ошибок
                    error ->
                Log.d("MyLog", "Volley Error: $error")
            }
        )

        queue.add(stringRequest)
    }

    private fun parseWeatherData(result: String) {
        val data = JSONObject(result)
        val listByDays = parseDays(data)
        parseCurrentData(data, listByDays[0])
    }

    //Парсим данные для текущего дня
    private fun parseCurrentData(obj: JSONObject, weatherItem: WeatherModel) {
        val item = WeatherModel(
            obj.getJSONObject("location").getString("name"),
            obj.getJSONObject("current").getString("last_updated"),
            obj.getJSONObject("current").getJSONObject("condition").getString("text"),
            obj.getJSONObject("current").getString("temp_c"),
            weatherItem.maxTemp, weatherItem.minTemp,
            obj.getJSONObject("current").getJSONObject("condition").getString("icon"),
            weatherItem.hours
        )

        model.liveDataCurrent.value = item

        Log.d("MyLog", "Item: $item")
    }

    //Парсим данные для списка дней
    private fun parseDays(obj: JSONObject): List<WeatherModel> {
        val list = ArrayList<WeatherModel>() //initial array
        val daysArray = obj.getJSONObject("forecast").getJSONArray("forecastday")
        val name = obj.getJSONObject("location").getString("name")

        //Формируем массив объектов с информацией по дням
        for (i in 0 until daysArray.length()) {
            val day = daysArray[i] as JSONObject

            val item = WeatherModel(
                name,
                day.getString("date"),
                day.getJSONObject("day").getJSONObject("condition").getString("text"),
                "",
                day.getJSONObject("day").getString("maxtemp_c").toFloat().toInt().toString(),
                day.getJSONObject("day").getString("mintemp_c").toFloat().toInt().toString(),
                day.getJSONObject("day").getJSONObject("condition").getString("icon"),
                day.getJSONArray("hour").toString()
            )
            list.add(item)
        }
        model.liveDataList.value = list
        return list
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()

    }
}