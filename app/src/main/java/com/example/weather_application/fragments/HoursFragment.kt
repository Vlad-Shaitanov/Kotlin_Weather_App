package com.example.weather_application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather_application.MainViewModel
import com.example.weather_application.adapters.WeatherAdapter
import com.example.weather_application.adapters.WeatherModel
import com.example.weather_application.databinding.FragmentHoursBinding
import org.json.JSONArray
import org.json.JSONObject


class HoursFragment : Fragment() {
    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter
    private val model: MainViewModel by activityViewModels()

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
        getHoursData()
    }

    //Подключение и настройка адаптера
    private fun initRcView() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter()
        rcView.adapter = adapter
    }

    private fun getHoursData() {
        model.liveDataCurrent.observe(viewLifecycleOwner) {
            adapter.submitList(getHoursList(it))
        }
    }

    private fun getHoursList(weatherItem: WeatherModel): List<WeatherModel> {
        val hoursArray = JSONArray(weatherItem.hours)
        val list = ArrayList<WeatherModel>()

        for (i in 0 until hoursArray.length()) {
            val imageUrl =
                (hoursArray[i] as JSONObject).getJSONObject("condition").getString("icon")
            val currentTemp = (hoursArray[i] as JSONObject).getString("temp_c")
            val item = WeatherModel(
                weatherItem.city,
                (hoursArray[i] as JSONObject).getString("time"),
                (hoursArray[i] as JSONObject).getJSONObject("condition").getString("text"),
                "${currentTemp}°C",
                "", "",
                (hoursArray[i] as JSONObject).getJSONObject("condition").getString("icon"),
                ""
            )

            list.add(item)
        }

        return list
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}