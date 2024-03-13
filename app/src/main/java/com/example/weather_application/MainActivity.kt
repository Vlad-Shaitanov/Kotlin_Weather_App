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
    }
}