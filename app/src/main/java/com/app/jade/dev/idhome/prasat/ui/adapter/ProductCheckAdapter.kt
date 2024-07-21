package com.app.jade.dev.idhome.prasat.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.jade.dev.idhome.prasat.R
import com.app.jade.dev.idhome.prasat.data.datasource.ProductCheck
import com.app.jade.dev.idhome.prasat.databinding.ItemProductWarehouseDesignForCheckProductBinding
import java.text.NumberFormat
import java.util.Locale

class ProductCheckAdapter(private var mContext: Context, private var productCheckList: List<ProductCheck>) : RecyclerView.Adapter<ProductCheckAdapter.GiWarehouseViewHolder>()  {
    inner class GiWarehouseViewHolder( var view: ItemProductWarehouseDesignForCheckProductBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCheckAdapter.GiWarehouseViewHolder {
        val binding = ItemProductWarehouseDesignForCheckProductBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return GiWarehouseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productCheckList.size
    }

    override fun onBindViewHolder(holder: GiWarehouseViewHolder, position: Int) {
        val giWarehouse = productCheckList[position]
        val view = holder.view

        val giWarehouseName = giWarehouse.giWarehouseName
        val giWarehouseLocation = giWarehouse.giWarehouseLocation
        val warehouseName = giWarehouse.warehouseName
        val warehouseLocation = giWarehouse.warehouseLocation
        val warehouseBalQty = giWarehouse.balQty.toInt().toString()
        val warehouseUnitCode = giWarehouse.unitCode
        val warehousePrice1 = formatCurrency(giWarehouse.price1)
        val warehousePrice2 = formatCurrency(giWarehouse.price2)
        val warehousePrice221 = formatCurrency(giWarehouse.price221)
        val warehousePrice222 = formatCurrency(giWarehouse.price222)

        val strGiWarehouseName = HtmlCompat.fromHtml(String.format(mContext.getString(R.string.tv_gi_warehouse_in_item_name_f),giWarehouseName), HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.tvGiWarehouseInItemGiWarehouseName.text = strGiWarehouseName

        val strGiWarehouseLocation = HtmlCompat.fromHtml(String.format(mContext.getString(R.string.tv_gi_warehouse_in_item_location_f),giWarehouseLocation), HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.tvGiWarehouseInItemGiWarehouseLocation.text = strGiWarehouseLocation

        val strWarehouseName = HtmlCompat.fromHtml(String.format(mContext.getString(R.string.tv_warehouse_in_item_name_f),warehouseName), HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.tvWarehouseInItemWarehouseName.text = strWarehouseName

        val strWarehouseLocation = HtmlCompat.fromHtml(String.format(mContext.getString(R.string.tv_warehouse_in_item_location_f),warehouseLocation), HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.tvWarehouseInItemWarehouseLocation.text = strWarehouseLocation

        val strWarehouseBalQty = HtmlCompat.fromHtml(String.format(mContext.getString(R.string.tv_warehouse_in_item_bal_qty_f),warehouseBalQty, warehouseUnitCode), HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.tvWarehouseInItemWarehouseBalQty.text = strWarehouseBalQty

        val strWarehousePrice1 = HtmlCompat.fromHtml(String.format(mContext.getString(R.string.tv_warehouse_in_item_price1_f),warehousePrice1, warehouseUnitCode), HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.tvWarehouseInItemWarehousePrice1.text = strWarehousePrice1

        val strWarehousePrice2 = HtmlCompat.fromHtml(String.format(mContext.getString(R.string.tv_warehouse_in_item_price2_f),warehousePrice2, warehouseUnitCode), HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.tvWarehouseInItemWarehousePrice2.text = strWarehousePrice2

        val strWarehousePrice221 = HtmlCompat.fromHtml(String.format(mContext.getString(R.string.tv_warehouse_in_item_price221_f),warehousePrice221, warehouseUnitCode), HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.tvWarehouseInItemWarehousePrice221.text = strWarehousePrice221

        val strWarehousePrice222 = HtmlCompat.fromHtml(String.format(mContext.getString(R.string.tv_warehouse_in_item_price222_f),warehousePrice222, warehouseUnitCode), HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.tvWarehouseInItemWarehousePrice222.text = strWarehousePrice222
    }

    private fun formatCurrency(amount: Float): String {
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        return formatter.format(amount.toDouble())
    }

}