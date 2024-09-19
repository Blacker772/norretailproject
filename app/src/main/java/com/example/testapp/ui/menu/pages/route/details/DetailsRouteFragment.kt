package com.example.testapp.ui.menu.pages.route.details

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.testapp.R
import com.example.testapp.databinding.FragmentDetailsRouteBinding
import com.example.testapp.ui.menu.pages.route.model.RouteModel


class DetailsRouteFragment : Fragment() {

    private var binding: FragmentDetailsRouteBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailsRouteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btBack?.setOnClickListener {
            findNavController().navigate(R.id.action_detailsRouteFragment_to_viewPagerFragment)
        }

        //Получение данных из аргументов
        val route = arguments?.let {
            if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable("route", RouteModel::class.java)
            } else {
                it.getParcelable("route")
            }
        }

        route?.let {routeData ->
            binding?.apply {
                sivArena.load(routeData.icon)
                tvCode.text = "Код: ${routeData.code}"
                tvName.text = "Наименование: ${routeData.name}"
                tvAddress.text = "Адрес: ${routeData.address}"
                tvPrice.text = "План: ${routeData.price}"
                tvSale.text = "Продано: ${routeData.sale}"
                tvTimeWork.text = "Время работы: ${routeData.time}"
                tvDayWork.text = "Дни работы: ${routeData.day}"
            }
        }
    }
}