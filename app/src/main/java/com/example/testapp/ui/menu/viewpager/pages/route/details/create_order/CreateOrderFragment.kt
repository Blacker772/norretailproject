package com.example.testapp.ui.menu.viewpager.pages.route.details.create_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.data.models.pages.ProductModel
import com.example.testapp.databinding.FragmentCreateOrderBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class CreateOrderFragment : Fragment() {

    private var binding: FragmentCreateOrderBinding? = null
    private val adapter = CreateOrderAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        binding = FragmentCreateOrderBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btBack?.setOnClickListener {
            findNavController().navigate(R.id.action_createOrderFragment_to_detailsRouteFragment)
        }
        
        binding?.rvItems?.adapter = adapter
        binding?.rvItems?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.updateRoutesList(listOfItems())
        adapter.onItemClickListener {
            bottomSheetDialog(it)
        }

        binding?.btnFilters?.setOnClickListener {
            showFiltersDialog()
        }
    }

    private fun listOfItems() = listOf(
        ProductModel(111,"Amstel", 199.00, 1500),
        ProductModel(112,"Heineken", 199.00, 6000),
        ProductModel(113,"Budweiser", 199.00, 1300),
        ProductModel(114,"Corona", 300.00, 500),
        ProductModel(115,"Stella Artois", 199.00, 10000),
        ProductModel(116,"Guinness", 300.00, 5000),
        ProductModel(117,"Modelo", 500.00, 100),
        ProductModel(118,"Leffe", 500.00, 2200),
        ProductModel(119,"Chimay", 399.00, 900),
        ProductModel(120,"Duvel", 399.00, 1000),
    )

    private fun bottomSheetDialog(product: ProductModel) {
        val dialog = BottomSheetDialog(requireContext())
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.layout_bottom_sheet, null)
        dialog.setContentView(view)

        val tvName = view.findViewById<TextView>(R.id.tvProductName)
        val tvArticles = view.findViewById<TextView>(R.id.tvProductArticles)
        val tvStock = view.findViewById<TextView>(R.id.tvProductStock)
        val tvPrice = view.findViewById<TextView>(R.id.tvProductPrice)

        tvName.text = product.name
        tvArticles.text = "Артикул: " + product.articles.toString()
        tvStock.text = "В наличии: " + product.stock.toString()
        tvPrice.text = "Cтоимость: " + product.price.toString() + " руб"

        val btClose = view.findViewById<Button>(R.id.btnAddToCart)
        btClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showFiltersDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_filter, null)
        dialog.setContentView(view)

        val btApplyFilters = view.findViewById<Button>(R.id.btnApplyFilters)
//        val etGroup = view.findViewById<EditText>(R.id.etGroup)

        btApplyFilters.setOnClickListener {
//            val group = etGroup.text.toString()
            dialog.dismiss()
        }
        dialog.show()
    }
}