package com.example.kotlinstart.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinstart.model.Repository
import com.example.kotlinstart.model.RepositoryIMPL
import com.example.kotlinstart.viewmodel.AppState
import java.lang.Thread.sleep

class MainViewModel(
    private val liveData:MutableLiveData<AppState> = MutableLiveData(),
    private val repository: RepositoryIMPL = RepositoryIMPL()
): ViewModel() {

    fun getData(): LiveData<AppState>{
        return liveData
    }
    fun getWeather(){
        Thread{
            liveData.postValue(AppState.Loading)
            if ((0..10).random() > 1)
                liveData.postValue(AppState.Success(repository.getWeatherFromServer()))
            else
                liveData.postValue(AppState.Error(IllegalAccessException()))
        }.start()
    }
}