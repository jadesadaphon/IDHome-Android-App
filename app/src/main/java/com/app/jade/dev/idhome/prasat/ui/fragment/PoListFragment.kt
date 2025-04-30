package com.app.jade.dev.idhome.prasat.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.jade.dev.idhome.prasat.databinding.FragmentPoListBinding
import com.app.jade.dev.idhome.prasat.ui.adapter.AdapterItemPo
import com.app.jade.dev.idhome.prasat.ui.viewmodel.PoManageActivityViewModel
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.jade.dev.idhome.prasat.R


class PoListFragment : Fragment() {

    private var _binding: FragmentPoListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: AdapterItemPo

    private val viewModel: PoManageActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AdapterItemPo { poData ->
            // เมื่อคลิก item แล้วให้เปิด Fragment ใหม่
            val detailFragment = PoDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("document_number", poData.documentNumber)
                }
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, detailFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.poRv.layoutManager = LinearLayoutManager(requireContext())
        binding.poRv.adapter = adapter
        observe()
    }

    private fun observe() {
        viewModel.listPo.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

