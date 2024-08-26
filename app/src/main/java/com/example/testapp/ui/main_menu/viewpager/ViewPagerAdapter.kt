package com.example.testapp.ui.main_menu.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testapp.ui.main_menu.pages.price.PriceListFragment
import com.example.testapp.ui.main_menu.pages.report.ReportFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ReportFragment()
            1 -> PriceListFragment()
            else -> ReportFragment()
        }
    }
}