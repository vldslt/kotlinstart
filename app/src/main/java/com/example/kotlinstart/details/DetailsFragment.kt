package com.example.kotlinstart.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinstart.databinding.FragmentDetailsBinding
import com.example.kotlinstart.repository.DTO.WeatherDTO
import com.example.kotlinstart.repository.OnServerResponse
import com.example.kotlinstart.repository.Weather
import com.example.kotlinstart.repository.WeatherLoader
import com.example.kotlinstart.utils.KEY_BUNDLE_WEATHER
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment(), OnServerResponse {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding  //утечка памяти
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_main, container, false)
        return binding.root
    }

    lateinit var currentCityName:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //var weather: Weather = requireArguments().getParcelable<Weather>(KEY_BUNDLE_WEATHER)!!
        arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER)?.let { //<--сейв колл,если аргументы null строка не выполнится
            currentCityName = it.city.name
            Thread{
                WeatherLoader(this@DetailsFragment).loadWeather(it.city.lat, it.city.lon)
            }.start()

        }

    }

    private fun renderData(weather: WeatherDTO) {
        with(binding) {
            loadingLayout.visibility = View.GONE
            cityName.text = currentCityName
            with(weather) {
                temperatureValue.text = weather.factDTO.temperature.toString()
                feelsLikeValue.text = weather.factDTO.feelsLike.toString()
                cityCoordinates.text = "${weather.infoDTO.lat} ${weather.infoDTO.lon}"
            }
        }
        Snackbar.make(binding.mainView, "DONE", Snackbar.LENGTH_LONG).show()
        //Toast.makeText(requireContext(),"progress done!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment;
        }
    }

    override fun onResponse(weatherDTO: WeatherDTO) {
        renderData(weatherDTO)
    }
}