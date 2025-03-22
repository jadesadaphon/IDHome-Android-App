package com.app.jade.dev.idhome.prasat.data.datasource

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class ProductSellDataStorage {

    private val productSellList = ArrayList<ProductSell>()

    suspend fun addProductsToSellList(jsonObject: JSONObject): MutableList<ProductSell> = withContext(Dispatchers.IO) {

        val data = jsonObject.getJSONObject("data")
        val barcode = jsonObject.getString("barcode")
        val productCode = data.getString("productCode")
        val productName = data.getString("productName")
        val warehouse = data.getJSONArray("warehouse")
        val amount = 1

        var prDocNo = ""
        var poDocNo = ""
        var poRemainQty = 0
        var poUnitCode = ""
        val prpo = data.optJSONObject("prpo") // ใช้ optJSONObject เพื่อลดความเสี่ยง NullPointerException
        if (prpo != null && prpo.length() != 0) {
            val pr = prpo.optJSONObject("pr")
            if (pr != null && pr.length() != 0) {
                prDocNo = pr.optString("docno", "") // ใช้ optString เพื่อตั้งค่า default เมื่อ key ไม่มีหรือเป็น null
            }
            val po = prpo.optJSONObject("po")
            if (po != null && po.length() != 0) {
                poDocNo = po.optString("docno", "")
                poRemainQty = po.optInt("remainqty", 0)
                poUnitCode = po.optString("unitcode", "")
            }
        }

        val stockList = arrayListOf<Int>()
        for(i in 0 until warehouse.length()){
            val item = warehouse.getJSONObject(i)
            val balQty = item.getString("balQty").toFloat().toInt()
            stockList.add(balQty)
        }
        val maximum = stockList.sum().toFloat().toInt()

        if (productSellList.size > 0){
            for(i in 0 until productSellList.size){
                val item = productSellList[i].barCode
                if (barcode == item){
                    val beforeAmount = productSellList[i].amount
                    val updateAmount =  amount + beforeAmount
                    if (maximum >= updateAmount){
                        productSellList[i] = ProductSell(barcode, productCode, productName, prDocNo, poDocNo,poRemainQty,poUnitCode, updateAmount, warehouse)
                        return@withContext productSellList
                    }
                    return@withContext productSellList
                }
            }
        }
        val item = ProductSell(barcode, productCode, productName, prDocNo, poDocNo, poRemainQty, poUnitCode, amount, warehouse)
        productSellList.add(0,item)
        return@withContext productSellList
    }



}