package com.example.weather_application.fragments

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.weather_application.databinding.FragmentMainBinding

class MainFragment : Fragment() {
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