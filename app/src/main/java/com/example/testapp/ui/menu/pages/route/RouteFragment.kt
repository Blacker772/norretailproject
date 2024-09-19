package com.example.testapp.ui.menu.pages.route

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.databinding.FragmentRouteBinding
import com.example.testapp.ui.menu.pages.route.model.RouteAdapter
import com.example.testapp.ui.menu.pages.route.model.RouteModel

class RouteFragment : Fragment() {

    private var binding: FragmentRouteBinding? = null
    private val adapter = RouteAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRouteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvRoute?.adapter = adapter
        binding?.rvRoute?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.updateRoutesList(listRoutes())
        adapter.onItemClickListener {
            val route = RouteModel(
                it.icon, it.code, it.name, it.address, it.price, it.sale, it.day, it.time
            )
            val bundle = Bundle()
            bundle.putParcelable("route", route)
            findNavController().navigate(R.id.action_viewPagerFragment_to_detailsRouteFragment, bundle)
        }
    }

    private fun listRoutes() = listOf(
        RouteModel(R.drawable.arena, "ДДАГАИ", "ИП БАЛЬЦЕР А.А.", "ДУДИНКА ДАЛЬНАЯ Б/Н", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ13М", "\"ПРИВОЗ\"", "ДУДИНКА ДУДИНСКАЯ 13", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ21М4", "ИП КОВАЛЕНКО Л.Т.", "ДУДИНКА ДУДИНСКАЯ 21", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ21М5", "ООО \"КОВАЛЕНКО\"", "ДУДИНКА ДУДИНСКАЯ 21", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ21М9", "ИП АГАЕВ Ш. Я.", "ДУДИНКА ДУДИНСКАЯ 21", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ32М", "ООО \"19 ПИКЕТ\"", "ДУДИНКА ДУДИНСКАЯ 36", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ32М", "ООО \"19 ПИКЕТ\"", "ДУДИНКА ДУДИНСКАЯ 36", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ32М", "ООО \"19 ПИКЕТ\"", "ДУДИНКА ДУДИНСКАЯ 36", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ32М", "ООО \"19 ПИКЕТ\"", "ДУДИНКА ДУДИНСКАЯ 36", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ32М", "ООО \"19 ПИКЕТ\"", "ДУДИНКА ДУДИНСКАЯ 36", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ32М", "ООО \"19 ПИКЕТ\"", "ДУДИНКА ДУДИНСКАЯ 36", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ32М", "ООО \"19 ПИКЕТ\"", "ДУДИНКА ДУДИНСКАЯ 36", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ32М", "ООО \"19 ПИКЕТ\"", "ДУДИНКА ДУДИНСКАЯ 36", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ32М", "ООО \"19 ПИКЕТ\"", "ДУДИНКА ДУДИНСКАЯ 36", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ32М", "ООО \"19 ПИКЕТ\"", "ДУДИНКА ДУДИНСКАЯ 36", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ32М", "ООО \"19 ПИКЕТ\"", "ДУДИНКА ДУДИНСКАЯ 36", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ32М", "ООО \"19 ПИКЕТ\"", "ДУДИНКА ДУДИНСКАЯ 36", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ32М", "ООО \"19 ПИКЕТ\"", "ДУДИНКА ДУДИНСКАЯ 36", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ32М", "ООО \"19 ПИКЕТ\"", "ДУДИНКА ДУДИНСКАЯ 36", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ32М", "ООО \"19 ПИКЕТ\"", "ДУДИНКА ДУДИНСКАЯ 36", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ32М", "ООО \"19 ПИКЕТ\"", "ДУДИНКА ДУДИНСКАЯ 36", "- руб", "- руб","ПН-ПТ", "9:00-21:00"),
        RouteModel(R.drawable.arena, "ДДУ32М", "ООО \"19 ПИКЕТ\"", "ДУДИНКА ДУДИНСКАЯ 36", "- руб", "- руб","ПН-ПТ", "9:00-21:00")
    )
}