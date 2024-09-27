package com.example.testapp.ui.menu.viewpager.vpcomponents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.testapp.R
import com.example.testapp.databinding.FragmentViewpagerBinding
import com.example.testapp.ui.SharedViewModel
import com.example.testapp.ui.menu.header.HeaderAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : Fragment() {

    private var binding: FragmentViewpagerBinding? = null
    private val adapter = HeaderAdapter()
    private val viewModel by viewModels<SharedViewModel>()
    private var header: View? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentViewpagerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vpAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)

        //Инициализация RV
        initRV()

        binding?.apply {

            //Привязка адаптера к ViewPager2
            viewPager2.adapter = vpAdapter

            //Синхронизация меню с перелистыванием фрагментов
            viewPager2.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    bottomNavigation.menu.children.forEachIndexed { index, _ ->
                        bottomNavigation.menu.getItem(position).setChecked(index == position)
                    }
                }
            })

            //Нижнее меню приложения
            bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.routeFragment -> {
                        changePosition(0)
                        return@setOnItemSelectedListener true
                    }

                    R.id.priceListFragment -> {
                        changePosition(1)
                        return@setOnItemSelectedListener true
                    }

                    R.id.reportFragment -> {
                        changePosition(2)
                        return@setOnItemSelectedListener true
                    }

                    else -> false
                }
            }

            //Поиск клиентов
            etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.setSearchQuery(newText ?: "")
                    return true
                }
            })

            //Кнопка открытия маршрута
            btAdd.setOnClickListener {
                findNavController().navigate(R.id.action_viewPagerFragment_to_yandexMapFragment)
            }
        }
    }

    //Перелистывание фрагментов
    private fun changePosition(position: Int) {
        binding?.apply {
            viewPager2.setCurrentItem(position, true)
        }
    }

    //Инициализация RV
    private fun initRV() {
        binding?.apply {
            header = navigationView.getHeaderView(0)
            val rvList = header?.findViewById<RecyclerView>(R.id.rvListButton)
            rvList?.adapter = adapter
            rvList?.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter.updateAddressList(viewModel.listOfButton())
            btMenu.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    //Удаление binding
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
