package com.example.testapp.ui.menu.viewpager.pages.route

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.databinding.FragmentRouteBinding
import com.example.testapp.data.pages.ClientModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RouteFragment : Fragment() {

    private var binding: FragmentRouteBinding? = null
    private val adapter = RouteAdapter()
    private val viewModel by viewModels<RouteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRouteBinding.inflate(inflater, container, false)
        initRV()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.state.collect {
                onChangeState(it)
            }
        }
        viewModel.getClients()


    }

    private fun initRV() {
        binding?.rvRoute?.adapter = adapter
        binding?.rvRoute?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapter.onItemClickListener {
            val client = ClientModel(
                it.id,
                it.nUser,
                it.name,
                it.deliveryAddress,
                it.legalAddress,
                it.inn,
                it.kpp,
                it.phoneNumber,
                it.boss,
                it.bossNumber
            )
            val bundle = Bundle()
            bundle.putParcelable("client", client)
            findNavController().navigate(
                R.id.action_viewPagerFragment_to_detailsRouteFragment,
                bundle
            )
        }
    }

    private fun onChangeState(state: UiStateRoute) {
        binding?.apply {
            when (state) {
                is UiStateRoute.Loading -> {
                    progressBar.isVisible = state.isLoading
                }

                is UiStateRoute.Error -> {
                    progressBar.isVisible = false
                    Toast.makeText(requireContext(), "${state.message}", Toast.LENGTH_SHORT).show()
                }

                is UiStateRoute.Data -> {
                    adapter.submitList(state.data)
                    progressBar.isVisible = false
                }

                else -> {
                    progressBar.isVisible = false
                }
            }
        }
    }
}