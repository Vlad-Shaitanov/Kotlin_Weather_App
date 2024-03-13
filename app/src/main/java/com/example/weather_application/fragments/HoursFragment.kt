package com.example.weather_application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather_application.adapters.WeatherAdapter
import com.example.weather_application.adapters.WeatherModel
import com.example.weather_application.databinding.FragmentHoursBinding


class HoursFragment : Fragment() {
    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Метод жизненного цикла фрагмента, когда элементы уже загрузились в память и есть на экране
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
    }

    //Подключение и настройка адаптера
    private fun initRcView() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter()
        rcView.adapter = adapter
        val list = listOf(
            WeatherModel(
                "", "12:00", "Sunny",
                "25°С", "", "", "", ""
            ),
            WeatherModel(
                "", "13:00", "Sunny",
                "28°С", "", "", "", ""
            ),
            WeatherModel(
                "", "14:00", "Sunny",
                "32°С", "", "", "", ""
            )
        )
        adapter.submitList(list)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}