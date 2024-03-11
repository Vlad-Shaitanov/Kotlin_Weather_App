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
import com.example.weather_application.R
import com.example.weather_application.adapters.VpAdapter
import com.example.weather_application.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator

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
    }

    private fun initViewAdapter() = with(binding){
        //Инициализация адаптера для Вью пейджера (передаем активити и список активити для переключения)
        val adapter = VpAdapter(activity as FragmentActivity, fragmentsList)

        //Подключения адаптера к Вью пейджеру
        vp.adapter = adapter

        // tabLayout и vp это id самого ТабЛейаута и вьюпейджера из верстки фрагмента
        TabLayoutMediator(tabLayout, vp) {
            tab, position ->
            //Указываем названия для табов
            tab.text = getString(layoutTabsList[position])
        }.attach()
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

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()

    }
}