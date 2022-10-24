package com.example.kotlinstart.repository.DTO

import android.os.Parcelable
import com.example.kotlinstart.repository.DTO.FactDTO
import com.example.kotlinstart.repository.DTO.ForecastDTO
import com.example.kotlinstart.repository.DTO.InfoDTO
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherDTO(
    @SerializedName("fact")
    val factDTO: FactDTO,
    @SerializedName("forecast")
    val forecastDTO: ForecastDTO,
    @SerializedName("info")
    val infoDTO: InfoDTO,
    @SerializedName("now")
    val now: Int,
    @SerializedName("now_dt")
    val nowDt: String
):Parcelable