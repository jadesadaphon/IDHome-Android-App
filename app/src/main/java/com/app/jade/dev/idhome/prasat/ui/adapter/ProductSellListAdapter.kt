package com.app.jade.dev.idhome.prasat.ui.adapter

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.jade.dev.idhome.prasat.R
import com.app.jade.dev.idhome.prasat.data.datasource.ProductSell
import com.app.jade.dev.idhome.prasat.databinding.ItemProductDesignForProductsSellListBinding
import com.app.jade.dev.idhome.prasat.ui.adapter.touchhelper.ProductSellListCustomItemTouchHelperCallback
import java.text.NumberFormat
import java.util.Collections
import java.util.Locale

class ProductSellListAdapter(private var mContext: Context, private var productSellList: MutableList<ProductSell>) : RecyclerView.Adapter<ProductSellListAdapter.ProductSellListViewHolder>() {

    inner class ProductSellListViewHolder(var view: ItemProductDesignForProductsSellListBinding ) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSellListViewHolder {
        val binding = ItemProductDesignForProductsSellListBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ProductSellListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productSellList.size
    }

    override fun onBindViewHolder(holder: ProductSellListViewHolder, position: Int) {
        val product = productSellList[position]
        val view = holder.view

//        view.imageViewProduct.setImageResource(
//            mContext.resources.getIdentifier(product.image, "drawable", mContext.packageName)
//        )

        val stockList = arrayListOf<Int>()
        for(i in 0 until product.warehouse.length()){
            val item = product.warehouse.getJSONObject(i)
            val balQty = item.getString("balQty").toFloat().toInt()
            stockList.add(balQty)
        }
        val stock = stockList.sum().toFloat()
        val strStock = formatCurrency(stock)

        val unitCode = product.warehouse.getJSONObject(0).getString("unitCode")
        val strPrice = formatCurrency(product.warehouse.getJSONObject(0).getString("price1").toFloat())
        val strPriceMember = formatCurrency(product.warehouse.getJSONObject(0).getString("price1").toFloat())
        val priceProMonth = product.warehouse.getJSONObject(0).getString("price221").toFloat()
        val priceProGrand = product.warehouse.getJSONObject(0).getString("price222").toFloat()
        val amount = product.amount


        val strProductName = HtmlCompat.fromHtml(String.format(mContext.getString(R.string.tv_product_name_in_item_product_f),product.productName), HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.tvProductName.text = strProductName

        val strProductPrice = HtmlCompat.fromHtml(String.format(mContext.getString(R.string.tv_product_price_in_item_product_f),strPrice,unitCode), HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.tvProductPrice.text = strProductPrice

        if (priceProMonth.toInt() != 0){
            val strPriceProMonth = formatCurrency(priceProMonth)
            view.tvProductPriceMonth.visibility = View.VISIBLE
            val strProductPriceProMonth = HtmlCompat.fromHtml(String.format(mContext.getString(R.string.tv_product_price_month_in_item_product_f),strPriceProMonth,unitCode), HtmlCompat.FROM_HTML_MODE_COMPACT)
            view.tvProductPriceMonth.text = strProductPriceProMonth
        }

        if (priceProGrand.toInt() != 0){
            val strPriceProGrand = formatCurrency(priceProGrand)
            view.tvProductPriceProGrand.visibility = View.VISIBLE
            val strProductPriceProGrand = HtmlCompat.fromHtml(String.format(mContext.getString(R.string.tv_product_price_pro_grand_in_item_product_f),strPriceProGrand,unitCode), HtmlCompat.FROM_HTML_MODE_COMPACT)
            view.tvProductPriceProGrand.text = strProductPriceProGrand
        }

        val strStockTotal = HtmlCompat.fromHtml(String.format(mContext.getString(R.string.tv_product_stock_in_item_product_f),strStock,unitCode), HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.tvProductStock.text = strStockTotal

        view.tvPcroductAmount.text = amount.toString()


        view.btnIvAdd.setOnClickListener {
            if (stock.toInt() > product.amount){
                product.amount++
                view.tvPcroductAmount.text = product.amount.toString()
                val sound = MediaPlayer.create(mContext, R.raw.beep)
                sound.start()
            }
        }

        view.btnIvRemove.setOnClickListener {
            if (product.amount > 1){
                product.amount--
                view.tvPcroductAmount.text = product.amount.toString()
                val sound = MediaPlayer.create(mContext, R.raw.beep)
                sound.start()
            }
        }


        view.cardViewProduct.setOnClickListener {
//            Snackbar.make(it, "${position}", Snackbar.LENGTH_SHORT).show()
        }



//        if(product.isDoping == true){
//            view.cardViewProduct.setCardBackgroundColor(Color.parseColor("#E6FBD4"))
//            view.textViewDescription.setTypeface(null, Typeface.BOLD)
//        }

    }

    private fun formatCurrency(amount: Float): String {
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        return formatter.format(amount.toDouble())
    }

}