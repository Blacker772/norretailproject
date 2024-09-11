package com.example.testapp.ui.menu.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.testapp.R
import com.example.testapp.databinding.FragmentViewpagerBinding
import com.example.testapp.ui.menu.header.HeaderAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : Fragment() {

    private var binding: FragmentViewpagerBinding? = null
    private val adapter = HeaderAdapter()
    private val viewModel by viewModels<VPViewModel>()
    private var header: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentViewpagerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
        adapter.updateAddressList(viewModel.listOfButton())
        binding?.btMenu?.setOnClickListener {
            binding?.drawerLayout?.openDrawer(GravityCompat.START)
        }

        //Создание адаптера
        val vpAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)

        //привязка адаптера к viewPager2
        binding?.viewPager2?.adapter = vpAdapter


        //синхронизация меню с перелистыванием фрагментов
        binding?.viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding?.bottomNavigation?.menu?.children?.forEachIndexed { index, _ ->
                    binding?.bottomNavigation?.menu?.getItem(position)?.setChecked(index == position)
                }
            }
        })

        //нижнее меню приложения
        binding?.bottomNavigation?.setOnItemSelectedListener {item ->
            when(item.itemId){
                R.id.routeFragment -> {
                    changePosition(0)
                    return@setOnItemSelectedListener true
                }
                R.id.priceListFragment ->{
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
    }

    //перелистывание фрагментов
    private fun changePosition(position: Int) {
        binding?.viewPager2?.setCurrentItem(position,true)
    }

    //инициализация списка
    private fun initRV() {
        header = binding?.navigationView?.getHeaderView(0)
        val rvList = header?.findViewById<RecyclerView>(R.id.rvListButton)
        rvList?.adapter = adapter
        rvList?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
