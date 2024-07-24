package com.app.jade.dev.idhome.prasat.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.jade.dev.idhome.prasat.data.datasource.api.idhome.TransferApi
import com.app.jade.dev.idhome.prasat.data.model.TransferViewModel
import com.app.jade.dev.idhome.prasat.databinding.FragmentTransferListBinding
import com.app.jade.dev.idhome.prasat.ui.adapter.TransferListAdapter
import kotlinx.coroutines.launch

class TransferListFragment : Fragment() {

    private companion object {
        private const val TAG = "TransferListFragment"
    }

    private val viewModel: TransferViewModel by activityViewModels()

    private lateinit var binding: FragmentTransferListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transferApi(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTransferListBinding.inflate(inflater, container, false)
        binding.transferRv.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        liveDataObserve()
    }

    private fun transferApi(context: Context) {
        lifecycleScope.launch {
            val transferApi = TransferApi()
            val jsonObject = transferApi.transferApi(context)
            if (jsonObject != null) {
                viewModel.addTransferToList(jsonObject)
            } else {
                Log.e(TAG, "Failed to fetch transfer details")
            }
        }
    }

    private fun liveDataObserve(){
        viewModel.transferList.observe(viewLifecycleOwner){
            val transferListAdapter = TransferListAdapter(requireContext(),it)
            binding.transferRv.adapter = transferListAdapter
        }


    }
}