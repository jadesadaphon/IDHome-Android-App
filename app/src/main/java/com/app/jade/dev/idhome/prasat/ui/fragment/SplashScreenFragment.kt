package com.app.jade.dev.idhome.prasat.ui.fragment

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.jade.dev.idhome.prasat.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewEvent()
    }

    private fun viewEvent(){
        binding.tvAppVersion.text = getAppVersion()
    }

    private fun getAppVersion(): String {
        try {
            val pInfo: PackageInfo = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
            return pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return "N/A"
    }

}