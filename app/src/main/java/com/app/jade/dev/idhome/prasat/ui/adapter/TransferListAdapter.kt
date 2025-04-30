package com.app.jade.dev.idhome.prasat.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.jade.dev.idhome.prasat.R
import com.app.jade.dev.idhome.prasat.data.datasource.Transfer
import com.app.jade.dev.idhome.prasat.databinding.ItemTransferDesignForTransferListBinding
import com.app.jade.dev.idhome.prasat.ui.activity.TransferActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TransferListAdapter(private var mContext: Context, private var transferList: List<Transfer>) : RecyclerView.Adapter<TransferListAdapter.TransferViewHolder>() {
    inner class TransferViewHolder( var view: ItemTransferDesignForTransferListBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransferViewHolder {
        val binding = ItemTransferDesignForTransferListBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return TransferViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return transferList.size
    }

    override fun onBindViewHolder(holder: TransferViewHolder, position: Int) {
        val transfer = transferList[position]
        val view = holder.view

        val transferCode = transfer.transferCode
        val strTransferCode = HtmlCompat.fromHtml(String.format(mContext.getString(R.string.tv_transfer_no_f),transferCode), HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.tvTransferNo.text = strTransferCode

        val personNameTh = transfer.personNameTh
        val strPersonNameTh = HtmlCompat.fromHtml(String.format(mContext.getString(R.string.tv_person_name_f),personNameTh), HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.tvPersonName.text = strPersonNameTh

        val dateTime = formatDateTime(transfer.sysCreate)
        val strDatetime = HtmlCompat.fromHtml(String.format(mContext.getString(R.string.tv_datetime_f),dateTime), HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.tvDatetime.text = strDatetime

        view.root.setOnClickListener {
            if (mContext is TransferActivity) {
                (mContext as TransferActivity).replaceFragment()
            }
        }
    }

    private fun formatDateTime(strDate: String): String {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH)
        val outputDateFormat = SimpleDateFormat("dd-MM-yyyy 'เวลา' HH:mm:ss", Locale.ENGLISH)
        try {
            val date = inputDateFormat.parse(strDate)
            val calendar = Calendar.getInstance()
            calendar.time = date!!
            calendar.add(Calendar.YEAR, 543)

            return outputDateFormat.format(calendar.time)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

}

