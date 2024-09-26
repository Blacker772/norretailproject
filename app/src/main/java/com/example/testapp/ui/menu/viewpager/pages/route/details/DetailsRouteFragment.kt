package com.example.testapp.ui.menu.viewpager.pages.route.details

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentDetailsRouteBinding
import com.example.testapp.data.pages.ClientModel


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

        binding?.btAdd?.setOnClickListener {
            findNavController().navigate(R.id.action_detailsRouteFragment_to_createOrderFragment)
        }

        //Получение данных из аргументов
        val route = arguments?.let {
            if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable("client", ClientModel::class.java)
            } else {
                it.getParcelable("client")
            }
        }

        route?.let {routeData ->
            binding?.apply {
                tvName.text = routeData.name
                tvAddress.text = routeData.deliveryAddress
            }
        }
    }
}