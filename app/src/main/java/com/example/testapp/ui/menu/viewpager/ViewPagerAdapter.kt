package com.example.testapp.ui.menu.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testapp.ui.menu.pages.price.PriceListFragment
import com.example.testapp.ui.menu.pages.report.ReportFragment
import com.example.testapp.ui.menu.pages.route.RouteFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> RouteFragment()
            1 -> PriceListFragment()
            else -> ReportFragment()
        }
    }
}