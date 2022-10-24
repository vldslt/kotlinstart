package com.example.kotlinstart.repository

class RepositoryIMPL:Repository {
    override fun getWeatherFromServer(): Weather {
        Thread.sleep(1500L)
        return Weather()
    }

    override fun getWorldWeatherFromLocalStorage(): List<Weather> {
        return getWorldCities()
    }

    override fun getRussianWeatherFromLocalStorage(): List<Weather> {
        return getRussianCities()
    }
}