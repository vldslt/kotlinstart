package com.example.kotlinstart.view.weatherlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinstart.repository.RepositoryIMPL
import com.example.kotlinstart.viewmodel.AppState

class MainViewModel(
    private val liveData:MutableLiveData<AppState> = MutableLiveData(),
    private val repository: RepositoryIMPL = RepositoryIMPL()
): ViewModel() {

    fun getData(): LiveData<AppState>{
        return liveData
    }

    fun getWeatherRussia() = getWeather(true)
    fun getWeatherWorla() = getWeather(false)

    private fun getWeather(isRussian:Boolean){
        Thread{
            liveData.postValue(AppState.Loading)
            if ((0..10).random() > 0) {
                val answer = if (!isRussian) repository.getWorldWeatherFromLocalStorage()
                else repository.getRussianWeatherFromLocalStorage()
                liveData.postValue(AppState.Success(answer))
            }
            else
                liveData.postValue(AppState.Error(IllegalAccessException()))
        }.start()
    }
}