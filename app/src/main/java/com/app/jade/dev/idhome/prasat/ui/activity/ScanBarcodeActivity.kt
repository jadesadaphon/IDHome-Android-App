package com.app.jade.dev.idhome.prasat.ui.activity

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.app.jade.dev.idhome.prasat.data.datasource.api.idhome.ProductSearchApi
import com.app.jade.dev.idhome.prasat.data.model.ProductViewModel
import com.app.jade.dev.idhome.prasat.databinding.ActivityScanBarcodeBinding
import com.app.jade.dev.idhome.prasat.ui.fragment.CheckProductFragment
import com.app.jade.dev.idhome.prasat.ui.fragment.SellProductsListFragment
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.launch

class ScanBarcodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBarcodeBinding
    private val viewModel: ProductViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showCamera()
            }
        }

    private val scanLauncher =
        registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.updateBarCode(result.contents)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        supportActionBar?.hide()
//        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        viewModel.eventName.value = intent.getStringExtra("eventName")
        initBinding()
        initView()
        liveDataObserve()
    }

    private fun initBinding() {

        binding = ActivityScanBarcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (viewModel.eventName.value == "CheckProduct"){
            supportFragmentManager.beginTransaction().replace(binding.fragmentContainerView.id, CheckProductFragment()).commit()
        }else if (viewModel.eventName.value == "Sell"){
            supportFragmentManager.beginTransaction().replace(binding.fragmentContainerView.id, SellProductsListFragment()).commit()
        }

    }

    private fun initView() {
        binding.btnScanInActivityPos.setOnClickListener {
            checkPermissionCamera(this)
        }
    }

    private fun liveDataObserve(){
        val barCodeObserver = Observer<String> { code ->
            getProductDetails(this, code)
        }
        viewModel.barCode.observe(this, barCodeObserver)
    }


    private fun getProductDetails(context: ScanBarcodeActivity, barCode: String) {
        lifecycleScope.launch {
            val productSearchApi = ProductSearchApi()
            val jsonObject = productSearchApi.productSearch(context,barCode)
            if (jsonObject != null) {
                viewModel.addProductsToSellList(jsonObject)
            } else {
                Log.e("ScanBarcodeActivity", "Failed to fetch product details")
            }
        }
    }

    private fun checkPermissionCamera(context: Context) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            showCamera()
        } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            Toast.makeText(context, "CAMERA permission required", Toast.LENGTH_SHORT).show()
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
        options.setPrompt("Scanning")
        options.setCameraId(0)  // หรือเปลี่ยนเป็น 1 ถ้าต้องการใช้กล้องหน้า
        options.setBeepEnabled(true)
        options.setBarcodeImageEnabled(false)
        options.setOrientationLocked(false)
        scanLauncher.launch(options)
    }



}