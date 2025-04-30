package com.app.jade.dev.idhome.prasat.ui.fragment

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.app.jade.dev.idhome.prasat.R
import com.app.jade.dev.idhome.prasat.databinding.FragmentPoDetailBinding
import com.app.jade.dev.idhome.prasat.ui.data.PoData
import com.app.jade.dev.idhome.prasat.ui.viewmodel.PoManageActivityViewModel

class PoDetailFragment : Fragment() {

    private val tag = "PoDetailFragment"

    private var _binding: FragmentPoDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PoManageActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPoDetailBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        val documentNumber = arguments?.getString("document_number")
        try {
            val listPo: List<PoData> = viewModel.listPo.value ?: emptyList()
            val docPo = listPo.find { it.documentNumber == documentNumber }
            if (docPo != null) {

                binding.tvPoDocumentNumber.text = Html.fromHtml(
                    getString(R.string.tv_po_document_number, docPo.documentNumber),
                    Html.FROM_HTML_MODE_LEGACY
                )

                binding.tvPoDocumentDate.text = Html.fromHtml(
                    getString(R.string.tv_po_document_date, docPo.documentDate),
                    Html.FROM_HTML_MODE_LEGACY
                )

                binding.tvPoCredit.text = Html.fromHtml(
                    getString(R.string.tv_po_credit, docPo.credit),
                    Html.FROM_HTML_MODE_LEGACY
                )

                binding.tvPoDeliveryDate.text = Html.fromHtml(
                    getString(R.string.tv_po_delivery_date, docPo.deliveryDate),
                    Html.FROM_HTML_MODE_LEGACY
                )

                binding.tvPoDeliveryMethod.text = Html.fromHtml(
                    getString(R.string.tv_po_delivery_method, docPo.deliveryMethod),
                    Html.FROM_HTML_MODE_LEGACY
                )

                binding.tvPoVendorCode.text = Html.fromHtml(
                    getString(R.string.tv_po_vendor_code, docPo.documentNumber),
                    Html.FROM_HTML_MODE_LEGACY
                )

                binding.tvPoVendorName.text = Html.fromHtml(
                    getString(R.string.tv_po_vendor_name, docPo.documentNumber),
                    Html.FROM_HTML_MODE_LEGACY
                )

                binding.tvPoCreatedBy.text = Html.fromHtml(
                    getString(R.string.tv_po_created_by, docPo.documentNumber),
                    Html.FROM_HTML_MODE_LEGACY
                )


                binding.tvPoTotalAmount.text = Html.fromHtml(
                    getString(R.string.tv_po_total_amount, docPo.documentNumber),
                    Html.FROM_HTML_MODE_LEGACY
                )


                binding.tvPoDiscountOnInvoice.text = Html.fromHtml(
                    getString(R.string.tv_po_discount_on_invoice, docPo.documentNumber),
                    Html.FROM_HTML_MODE_LEGACY
                )


                binding.tvPoDiscountAmount.text = Html.fromHtml(
                    getString(R.string.tv_po_discount_amount, docPo.documentNumber),
                    Html.FROM_HTML_MODE_LEGACY
                )


                binding.tvPoProductValue.text = Html.fromHtml(
                    getString(R.string.tv_po_product_value, docPo.documentNumber),
                    Html.FROM_HTML_MODE_LEGACY
                )


                binding.tvPoVatRate.text = Html.fromHtml(
                    getString(R.string.tv_po_vat_rate, docPo.documentNumber),
                    Html.FROM_HTML_MODE_LEGACY
                )

                binding.tvPoVatAmount.text = Html.fromHtml(
                    getString(R.string.tv_po_vat_amount, docPo.documentNumber),
                    Html.FROM_HTML_MODE_LEGACY
                )
                binding.tvPoTotalAmountWithVat.text = Html.fromHtml(
                    getString(R.string.tv_po_total_amount_with_vat, docPo.documentNumber),
                    Html.FROM_HTML_MODE_LEGACY
                )
                binding.tvPoExemptValue.text = Html.fromHtml(
                    getString(R.string.tv_po_exempt_value, docPo.documentNumber),
                    Html.FROM_HTML_MODE_LEGACY
                )
                binding.tvPoGrandTotal.text = Html.fromHtml(
                    getString(R.string.tv_po_grand_total, docPo.documentNumber),
                    Html.FROM_HTML_MODE_LEGACY
                )



            } else {
                Log.w("PoDetailFragment", "Document not found")
            }
        } catch (e: Exception) {
            Log.e(tag, "error: ${e.message}")
        }
    }
}
