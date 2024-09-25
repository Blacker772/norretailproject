package com.example.testapp.ui.menu.viewpager.pages.route.points

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.databinding.FragmentPointBinding

class PointFragment : Fragment() {

    private var binding: FragmentPointBinding? = null
    private val adapter = PointAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPointBinding.inflate(inflater, container, false)
        initRV()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {

            btBack.setOnClickListener {
                findNavController().navigate(R.id.action_yandexMapFragment_to_viewPagerFragment)
            }
            btBuildRoute.setOnClickListener {
                openYandexNavigator(listOfPoints)
            }
        }
    }

    private fun openYandexNavigator(points: List<Shop>) {
        val rText = points.joinToString("~") { "${it.latitude},${it.longitude}" }
        val uri = "yandexmaps://maps.yandex.ru/?rtext=$rText&rtt=auto"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri)).apply {
            `package` = "ru.yandex.yandexmaps"
        }
        startActivity(intent)
    }

    private fun initRV(){
        binding?.apply {
            rvPoints.adapter = adapter
            rvPoints.layoutManager = LinearLayoutManager(requireContext())
            adapter.updateRoutesList(listOfPoints)
        }
    }
    private val listOfPoints = listOf(
        Shop(R.drawable.ic_shop, "Зеленая линия Премиум", "г.Норильск, ул. Ленинский пр-т д. 5", 69.343605, 88.209097),
        Shop(R.drawable.ic_shop, "Зеленая линия", "г.Норильск, ул. Бегичева д. 7", 69.361750, 88.192143),
        Shop(R.drawable.ic_shop, "Океан", "г.Норильск, ул. Бегичева д. 24", 69.363464, 88.180629)
    )
}