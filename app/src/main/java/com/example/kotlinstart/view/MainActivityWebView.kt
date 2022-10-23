package com.example.kotlinstart.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.os.Handler
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import com.example.kotlinstart.R
import com.example.kotlinstart.databinding.ActivityMainBinding
import com.example.kotlinstart.databinding.ActivityMainWebviewBinding
import com.example.kotlinstart.view.weatherlist.WeatherListFragment
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MainActivityWebView : AppCompatActivity() {
    lateinit var binding: ActivityMainWebviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ok.setOnClickListener {
            val urlText = binding.etTextUrl.text.toString()

            // binding.webview.loadUrl(urlText) -- заменяет строки 29-49

            val uri = URL(urlText)
            val urlConnection: HttpsURLConnection =
                (uri.openConnection() as HttpsURLConnection).apply {
                    connectTimeout = 1000
                    readTimeout = 1000
                }

            Thread{
                val headers = urlConnection.headerFields
                val buffer =
                    BufferedReader(InputStreamReader(urlConnection.inputStream)) // выводит сайт построчно
                val result = getLinesAsOneBigString(buffer)
                    /*runOnUiThread{ // запуск в основном потоке 1 способ
                    binding.webview.loadData(result,"tet/html; utf-8", "utf-8")
                }*/
                Handler(Looper.getMainLooper()).post { // 2 способ
                    binding.webview.loadDataWithBaseURL(null,result,"tet/html; utf-8", "utf-8", null)
                }
            }.start() // загрузили отдельным потоком
        }
    }

    fun getLinesAsOneBigString(bufferedReader: BufferedReader): String {
        return bufferedReader.lines().collect(Collectors.joining("\n"));
    } // читаем весь юрл одной строкой
}




