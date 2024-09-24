package com.example.testapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.testapp.R
import com.example.testapp.databinding.FragmentYandexMapBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapEvent
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView

class YandexMapFragment : Fragment() {

    private var binding: FragmentYandexMapBinding? = null
    private lateinit var geoObjectTapListener: GeoObjectTapListener


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        MapKitFactory.initialize(requireContext())
        binding = FragmentYandexMapBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Инициализация карты
        val mapView = binding?.yandexMap

        //Фокус карты на Норильск
        mapView?.mapWindow?.map?.move(
            CameraPosition(
                Point(69.3558, 88.1893),
                13f,
                0f,
                0f
            )
        )

        //Слушатель карты
        geoObjectTapListener = GeoObjectTapListener {
            val dialog = BottomSheetDialog(requireContext())
            val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_object, null)
            dialog.setContentView(dialogView)

            val tvName = dialogView.findViewById<TextView>(R.id.tvObjectName)
            val tvDescription = dialogView.findViewById<TextView>(R.id.tvObjectAddress)
            val btClose = dialogView.findViewById<Button>(R.id.closeObjectButton)

            tvName.text = it.geoObject.name
            tvDescription.text = it.geoObject.descriptionText
            dialog.show()

            btClose.setOnClickListener {
                dialog.dismiss()
            }
            true
        }

        mapView?.mapWindow?.map?.addTapListener(geoObjectTapListener)
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding?.yandexMap?.onStart()
    }

    override fun onStop() {
        binding?.yandexMap?.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        geoObjectTapListener.let { binding?.yandexMap?.mapWindow?.map?.removeTapListener(it) }
        binding = null
    }
}