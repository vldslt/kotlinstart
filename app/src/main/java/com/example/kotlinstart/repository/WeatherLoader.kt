package com.example.kotlinstart.repository

import android.os.Handler
import android.os.Looper
import com.example.kotlinstart.repository.DTO.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(val onServerResponseListener: OnServerResponse) {
    fun loadWeather(lat: Double, lon: Double){
        Thread {
            try{
            val urlText = "https://api.weather.yandex.ru/v2/forecast?lat=$lat&lon=$lon"

            // binding.webview.loadUrl(urlText) -- заменяет строки 29-49

            val uri = URL(urlText)
            val urlConnection: HttpsURLConnection =
                (uri.openConnection() as HttpsURLConnection).apply {
                    connectTimeout = 1000
                    readTimeout = 1000
                    addRequestProperty("X-Yandex-API-Key","4ed0ac89-e1f4-4e83-9ad0-63cc43a1401a")
                }
            val headers = urlConnection.headerFields
            val buffer =
                BufferedReader(InputStreamReader(urlConnection.inputStream)) // выводит сайт построчно
            val result = (buffer.toString())
            val weatherDTO: WeatherDTO = Gson().fromJson(result, WeatherDTO::class.java)
            // библиотека видит строку и переводит в набор объектов

            Handler(Looper.getMainLooper()).post { onServerResponseListener.onResponse(weatherDTO)
            }
            }catch (e :java.lang.Exception) {

            } finally {
                //  urlConnection.disconnect()
            }
        }.start()
    }
}