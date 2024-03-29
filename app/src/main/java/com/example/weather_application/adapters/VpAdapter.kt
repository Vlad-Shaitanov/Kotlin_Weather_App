package com.example.weather_application.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

//Адаптер для вью пейджера
class VpAdapter(
    fragmentActivity: FragmentActivity,
    private val list: List<Fragment>
): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {

        //Возвращаем фрагмент, который хотим запустить
        return list[position]
    }
}