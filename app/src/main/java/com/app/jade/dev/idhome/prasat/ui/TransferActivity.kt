package com.app.jade.dev.idhome.prasat.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.jade.dev.idhome.prasat.R
import com.app.jade.dev.idhome.prasat.databinding.ActivityScanBarcodeBinding
import com.app.jade.dev.idhome.prasat.databinding.ActivityTransferBinding
import com.app.jade.dev.idhome.prasat.ui.fragment.CheckProductFragment
import com.app.jade.dev.idhome.prasat.ui.fragment.TransferListFragment

class TransferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(binding.fragmentContainerView.id, TransferListFragment()).commit()
    }
}