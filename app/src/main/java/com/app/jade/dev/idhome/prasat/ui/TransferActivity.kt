package com.app.jade.dev.idhome.prasat.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.jade.dev.idhome.prasat.databinding.ActivityTransferBinding
import com.app.jade.dev.idhome.prasat.ui.fragment.TransferDetailsFragment
import com.app.jade.dev.idhome.prasat.ui.fragment.TransferListFragment

class TransferActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransferBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(binding.fragmentContainerView.id, TransferListFragment()).commit()
    }
    fun replaceFragment() {
        supportFragmentManager.beginTransaction().replace(binding.fragmentContainerView.id, TransferDetailsFragment()).commit()
    }
}