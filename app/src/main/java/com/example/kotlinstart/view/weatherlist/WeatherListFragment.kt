package com.example.kotlinstart.view.weatherlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinstart.R
import com.example.kotlinstart.databinding.FragmentWeatherListBinding
import com.example.kotlinstart.details.DetailsFragment
import com.example.kotlinstart.model.Weather
import com.example.kotlinstart.utils.KEY_BUNDLE_WEATHER
import com.example.kotlinstart.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

class WeatherListFragment : Fragment(),OnItemListClickListener {

    private var _binding: FragmentWeatherListBinding?= null
    private val binding:FragmentWeatherListBinding  //утечка памяти
        get() {
            return _binding!!
        }

    val adapter = WeatherListAdapter(this)

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherListBinding.inflate(inflater,container,false)
        //return inflater.inflate(R.layout.fragment_main, container, false)
        return binding.root
    }
    var isRussian = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val observer = object:Observer<AppState> {
            override fun onChanged(data:AppState) {
                renderData(data)
            }
        }
        viewModel.getData().observe(viewLifecycleOwner,observer)
        binding.floatingActionButton.setOnClickListener {
            isRussian =!isRussian
            if(isRussian) {
                viewModel.getWeatherRussia()
                binding.floatingActionButton.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_russia))
            }else{
                binding.floatingActionButton.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_earth))
                viewModel.getWeatherWorla()

            }
        }
    }

    private fun renderData(data:AppState){
        when (data){
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.root,"not good ${data.error}", Snackbar.LENGTH_LONG).show()
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                adapter.setData(data.weatherList)
                /*binding.cityName.text = data.weatherData.city.name.toString()
                binding.temperatureValue.text = data.weatherData.temperature.toString()
                binding.feelsLikeValue.text = data.weatherData.feelsLike.toString()
                binding.cityCoordinates.text ="${data.weatherData.city.lat} ${data.weatherData.city.lon}"
                Snackbar.make(binding.mainView,"DONE", Snackbar.LENGTH_LONG).show()
                //Toast.makeText(requireContext(),"progress done!", Toast.LENGTH_SHORT).show()

                 */
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeatherListFragment()
    }

    override fun onItemClick(weather: Weather) {
        val bundle = Bundle()
        bundle.putParcelable(KEY_BUNDLE_WEATHER,weather)
        requireActivity().supportFragmentManager.beginTransaction().add(
            R.id.container, DetailsFragment.newInstance(bundle)).addToBackStack(" ").commit()
    }
}