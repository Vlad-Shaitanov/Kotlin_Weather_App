package com.example.weather_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather_application.databinding.ActivityMainBinding
import com.example.weather_application.fragments.MainFragment
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val keyWeather = BuildConfig.API_KEY_WEATHERAPI  //Ключ от Weather Api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*В главном активити будем открывать фрагмент
            Фрагмент будет добавлен поверх активити, поэтому в верстке
            актвити можно оставить только контейнер, а все остальное удалить
         */
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeHolder, MainFragment.newInstance())
            .commit()

//        binding.btnGet.setOnClickListener {
//            getData("London")
//        }

    }

//    private fun getData(name: String){
//        val url = "https://api.weatherapi.com/v1/current.json?key=$keyWeather&q=$name&aqi=no"
//
//        //Создали очередь для запросов Volley
//        val queue = Volley.newRequestQueue(this)
//        //Настройка запроса
//        val stringRequest = StringRequest(
//            Request.Method.GET,
//            url,
//            {response ->
//                val obj = JSONObject(response)
//
//                val temp_c = obj.getJSONObject("current").getString("temp_c")
//                Log.d("MyLog", "Volley response: $temp_c")
//                binding.tvTemp.text = temp_c
//            },
//            {
//                Log.d("MyLog", "Volley Error: $it")
//            }
//        )
//
//        queue.add(stringRequest)
//
//
//    }
}