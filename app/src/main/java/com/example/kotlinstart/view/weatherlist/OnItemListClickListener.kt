package com.example.kotlinstart.view.weatherlist

import com.example.kotlinstart.model.Weather

interface OnItemListClickListener {
    fun onItemClick (weather:Weather)
}