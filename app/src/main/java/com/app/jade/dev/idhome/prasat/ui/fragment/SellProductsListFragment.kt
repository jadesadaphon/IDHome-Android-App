package com.app.jade.dev.idhome.prasat.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.jade.dev.idhome.prasat.data.model.ProductViewModel
import com.app.jade.dev.idhome.prasat.databinding.FragmentListProductsSellBinding
import com.app.jade.dev.idhome.prasat.ui.adapter.ProductSellListAdapter
import com.app.jade.dev.idhome.prasat.ui.adapter.touchhelper.ProductSellListCustomItemTouchHelperCallback

class SellProductsListFragment : Fragment() {

    private val tag = "ProductsListFragment"
    private lateinit var binding: FragmentListProductsSellBinding
    private val viewModel: ProductViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListProductsSellBinding.inflate(inflater, container, false)
        binding.productsRv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.productSellList.observe(viewLifecycleOwner){
            val productSellListAdapter = ProductSellListAdapter(requireContext(),it)

            binding.productsRv.adapter = productSellListAdapter

            val callback = ProductSellListCustomItemTouchHelperCallback(requireContext() , 0, ItemTouchHelper.LEFT)
            val itemTouchHelper = ItemTouchHelper(callback)
            itemTouchHelper.attachToRecyclerView(binding.productsRv)

        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    private fun initView() {}


}