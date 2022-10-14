package com.example.kotlinstart.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinstart.databinding.FragmentDetailsBinding
import com.example.kotlinstart.model.Weather
import com.example.kotlinstart.utils.KEY_BUNDLE_WEATHER
import com.example.kotlinstart.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment() {

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
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var weather: Weather = requireArguments().getParcelable<Weather>(KEY_BUNDLE_WEATHER)!!
        //arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER) <--сейв колл,если аргументы null строка не выполнится
        renderData(weather)
    }

    private fun renderData(weather: Weather) {
        binding.loadingLayout.visibility = View.GONE
        binding.cityName.text = weather.city.name.toString()
        binding.temperatureValue.text = weather.temperature.toString()
        binding.feelsLikeValue.text = weather.feelsLike.toString()
        binding.cityCoordinates.text = "${weather.city.lat} ${weather.city.lon}"
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
}