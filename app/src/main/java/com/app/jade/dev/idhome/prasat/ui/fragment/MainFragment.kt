package com.app.jade.dev.idhome.prasat.ui.fragment

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.jade.dev.idhome.prasat.R
import com.app.jade.dev.idhome.prasat.databinding.FragmentMainBinding
import com.app.jade.dev.idhome.prasat.ui.ScanBarcodeActivity
import com.app.jade.dev.idhome.prasat.ui.TransferActivity

class MainFragment : Fragment() {

    private val tag = "MainFragment"

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    private fun initView() {
        binding.btnSell.setOnClickListener {
            val intent = Intent(requireContext(), ScanBarcodeActivity::class.java)
            val eventName = "Sell"
            intent.putExtra("eventName", eventName)
            startActivity(intent)
        }

        binding.btnCheckProduct.setOnClickListener {
            val intent = Intent(requireContext(), ScanBarcodeActivity::class.java)
            val eventName = "CheckProduct"
            intent.putExtra("eventName", eventName)
            startActivity(intent)
        }

        binding.btnTransfer.setOnClickListener {
            val intent = Intent(requireContext(), TransferActivity::class.java)
            startActivity(intent)
        }
    }

}