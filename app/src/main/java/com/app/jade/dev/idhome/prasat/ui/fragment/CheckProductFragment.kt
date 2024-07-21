package com.app.jade.dev.idhome.prasat.ui.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat.getColor
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.jade.dev.idhome.prasat.R
import com.app.jade.dev.idhome.prasat.data.model.ProductViewModel
import com.app.jade.dev.idhome.prasat.databinding.FragmentCheckProductBinding
import com.app.jade.dev.idhome.prasat.ui.adapter.ProductCheckAdapter
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer
import com.journeyapps.barcodescanner.BarcodeEncoder


class CheckProductFragment : Fragment() {

    private val tag = "CheckProductFragment"
    private lateinit var binding: FragmentCheckProductBinding
    private val viewModel: ProductViewModel by activityViewModels()

    private fun liveDataObserve(){
        viewModel.productSellList.observe(viewLifecycleOwner){
            val barCode = it[0].barCode
            val productCode = it[0].productCode
            val productName = it[0].productName
            val warehouse = it[0].warehouse
            displayBitmap(barCode)
            val strProductCode = HtmlCompat.fromHtml(String.format(this.getString(R.string.tv_product_code_in_fm_check_product_f),productCode), HtmlCompat.FROM_HTML_MODE_COMPACT)
            binding.tvProductCodeInDetails.text = strProductCode

            val strProductName = HtmlCompat.fromHtml(String.format(this.getString(R.string.tv_product_name_in_fm_check_product_f),productName), HtmlCompat.FROM_HTML_MODE_COMPACT)
            binding.tvProductNameInItemDetails.text = strProductName

            for(i in 0 until warehouse.length()){
                val item = warehouse.getJSONObject(i)
                viewModel.addProductCheckToList(item)
            }
        }

        viewModel.productCheckList.observe(viewLifecycleOwner){
            val productCheckAdapter = ProductCheckAdapter(requireContext(),it)
            binding.giWarehouseRv.adapter = productCheckAdapter
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCheckProductBinding.inflate(inflater, container, false)
        binding.giWarehouseRv.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        liveDataObserve()
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    private fun initView() {}


    private fun displayBitmap(value: String) {
        val widthPixels = resources.getDimensionPixelSize(R.dimen.width_barcode)
        val heightPixels = resources.getDimensionPixelSize(R.dimen.height_barcode)

        binding.imageBarcode.setImageBitmap(
            createBarcodeBitmap(
                barcodeValue = value,
                barcodeColor = getColor(requireContext(), R.color.black),
                backgroundColor = getColor(requireContext(),android.R.color.white),
                widthPixels = widthPixels,
                heightPixels = heightPixels
            )
        )
        binding.textBarcodeNumber.text = value
    }

    private fun createBarcodeBitmap(barcodeValue: String, @ColorInt barcodeColor: Int, @ColorInt backgroundColor: Int, widthPixels: Int, heightPixels: Int): Bitmap {
        val bitMatrix = Code128Writer().encode(barcodeValue, BarcodeFormat.CODE_128, widthPixels, heightPixels)
        val pixels = IntArray(bitMatrix.width * bitMatrix.height)
        for (y in 0 until bitMatrix.height) {
            val offset = y * bitMatrix.width
            for (x in 0 until bitMatrix.width) {
                pixels[offset + x] =
                    if (bitMatrix.get(x, y)) {
                        barcodeColor
                    }else{
                        backgroundColor
                    }
            }
        }
        val bitmap = Bitmap.createBitmap(bitMatrix.width, bitMatrix.height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, bitMatrix.width, 0, 0, bitMatrix.width, bitMatrix.height)
        return bitmap
    }

}