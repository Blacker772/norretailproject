package com.example.testapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testapp.R
import com.example.testapp.databinding.FragmentYandexMapBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView

@Suppress("DEPRECATION")
class YandexMapFragment : Fragment() {


    private var bidning: FragmentYandexMapBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        MapKitFactory.initialize(requireContext())
        bidning = FragmentYandexMapBinding.inflate(inflater, container, false)
        return bidning?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val map = bidning?.yandexMap?.map
        map?.move(CameraPosition(
            Point(69.3558, 88.1893),
            13f,
            0f,
            0f
        ))
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        bidning?.yandexMap?.onStart()
    }

    override fun onStop() {
        bidning?.yandexMap?.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bidning = null
    }
}