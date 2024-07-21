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
import com.app.jade.dev.idhome.prasat.data.datasource.api.idhome.LoginApi
import com.app.jade.dev.idhome.prasat.data.model.EkycViewModel
import com.app.jade.dev.idhome.prasat.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: EkycViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        liveDataObserve()
        initView()
    }

    private fun initView() {
        binding.btnLogin.setOnClickListener {
            val vCode = binding.textInputEditTextCode
            val vPassword = binding.textInputEditTextPassword
            val code: String = vCode.text.toString()
            val password: String = vPassword.text.toString()
            if (code.isBlank()){
                vCode.error = "รหัสพนักงานต้องต้องไม่ว่างเปล่า"
                return@setOnClickListener
            }
            if (password.isBlank()){
                vPassword.error = "รหัสผ่านต้องไม่ว่างเปล่า"
                return@setOnClickListener
            }
            disabledFrom()
            loginApi(requireContext(),code, password)
        }
    }

    private fun disabledFrom(){
        binding.textInputEditTextCode.isEnabled = false
        binding.textInputEditTextPassword.isEnabled = false
        binding.btnLogin.isEnabled = false
        binding.tvLoginErr.visibility = View.INVISIBLE
        binding.loginLoadingLayout.visibility = View.VISIBLE
    }

    private fun enabledFrom(){
        binding.textInputEditTextCode.isEnabled = true
        binding.textInputEditTextPassword.isEnabled = true
        binding.btnLogin.isEnabled = true
        binding.loginLoadingLayout.visibility = View.INVISIBLE
    }

    private fun liveDataObserve(){
        viewModel.networkType.observe(viewLifecycleOwner){
            if (it != 1){
                binding.btnLogin.isEnabled = false
                binding.tvErrorNetwork.visibility = View.VISIBLE
            }else{
                binding.btnLogin.isEnabled = true
                binding.tvErrorNetwork.visibility = View.INVISIBLE
            }
        }

        viewModel.apiResponse.observe(viewLifecycleOwner){
            if (it.has("message")){
                enabledFrom()
                binding.tvLoginErr.visibility = View.VISIBLE
                return@observe
            }
            viewModel.userData.value = it
        }
    }

    private fun loginApi(context: Context, code: String, password: String) {
        lifecycleScope.launch {
            val loginApi = LoginApi()
            val jsonObject = loginApi.loginApi(context,code,password)
            if (jsonObject != null) {
                viewModel.apiResponse.value = jsonObject
            } else {
                Log.e("ScanBarcodeActivity", "Failed to fetch product details")
            }
        }
    }

}