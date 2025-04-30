package com.app.jade.dev.idhome.prasat.ui.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.jade.dev.idhome.prasat.Application
import com.app.jade.dev.idhome.prasat.R
import com.app.jade.dev.idhome.prasat.ui.data.PoData

class AdapterItemPo(
    private val onItemClick: (PoData) -> Unit
) : ListAdapter<PoData, AdapterItemPo.ItemPoHolder>(DiffCallback()) {


    private val context = Application.AppContext

    inner class ItemPoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDocumentNumber: TextView = itemView.findViewById(R.id.tv_document_number)
        val tvCreatedBy: TextView = itemView.findViewById(R.id.tv_created_by)
        val tvVendorName: TextView = itemView.findViewById(R.id.tv_vendor_name)
        val tvGrandTotal: TextView = itemView.findViewById(R.id.tv_grand_total)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPoHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_po_design_for_po_manage_list, parent, false)
        return ItemPoHolder(view)
    }

    override fun onBindViewHolder(holder: ItemPoHolder, position: Int) {
        val item = getItem(position)

        holder.tvDocumentNumber.text = Html.fromHtml(
            context.getString(R.string.tv_po_document_number, item.documentNumber),
            Html.FROM_HTML_MODE_LEGACY
        )
        holder.tvCreatedBy.text = Html.fromHtml(
            context.getString(R.string.tv_po_created_by, item.createdBy),
            Html.FROM_HTML_MODE_LEGACY
        )
        holder.tvVendorName.text = Html.fromHtml(
            context.getString(R.string.tv_po_vendor_name, item.vendorName),
            Html.FROM_HTML_MODE_LEGACY
        )
        holder.tvGrandTotal.text = Html.fromHtml(
            context.getString(R.string.tv_po_grand_total, item.grandTotal.toString()),
            Html.FROM_HTML_MODE_LEGACY
        )

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PoData>() {
        override fun areItemsTheSame(oldItem: PoData, newItem: PoData): Boolean {
            return oldItem.documentNumber == newItem.documentNumber
        }

        override fun areContentsTheSame(oldItem: PoData, newItem: PoData): Boolean {
            return oldItem == newItem
        }
    }
}
