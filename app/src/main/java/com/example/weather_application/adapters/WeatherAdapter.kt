package com.example.weather_application.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_application.R
import com.example.weather_application.databinding.ListItemBinding
import com.squareup.picasso.Picasso

class WeatherAdapter(val listener: Listener?) :
    ListAdapter<WeatherModel, WeatherAdapter.Holder>(Comparator()) {

    //Класс хранит ссылки на все элементы одного айтема списка (У каждого элемента будет свой холдер)
    class Holder(view: View, val listener: Listener?) : RecyclerView.ViewHolder(view) {
        val binding = ListItemBinding.bind(view)
        var itemTemporary: WeatherModel? = null

        init {
            //Прослушиваем событие на записях во вкладке по дням
            itemView.setOnClickListener {
                // let позволяет запустить функцию только есть значение в itemTemporary
                itemTemporary?.let { it1 -> listener?.onClick(it1) }
            }
        }

        //Заполняем элементы данными
        fun bind(item: WeatherModel) = with(binding) {
            itemTemporary = item
            tvDate.text = item.time
            tvCondition.text = item.condition
            tvTemp.text =
                item.currentTemp.ifEmpty { "${item.maxTemp}°C / ${item.minTemp}°C" } //Если item.currentTemp пуст, то значение будет взято из {}
            Picasso.get()
                .load("https:${item.imageUrl}")
                .into(ivItem)
        }
    }

    //Класс нужен для сравнения элементов списка. (Если элемент не менялся, то он не будет перерисовываться)
    class Comparator : DiffUtil.ItemCallback<WeatherModel>() {
        override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            //В идеале нужно сравнить по уникальному элементу из модели (по id)
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem
        }

    }

    //Функция запустится столько раз, сколько элементов в списке
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //Создаем вью
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        //Возвращаем вью из функции
        return Holder(view, listener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //Заполняем созданный вью
        holder.bind(getItem(position))
    }

    interface Listener {
        fun onClick(item: WeatherModel)
    }
}