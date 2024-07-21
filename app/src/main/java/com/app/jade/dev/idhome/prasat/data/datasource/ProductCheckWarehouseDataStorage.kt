package com.app.jade.dev.idhome.prasat.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class ProductCheckWarehouseDataStorage {

    private val productCheckList = ArrayList<ProductCheck>()

    suspend fun addProductCheckList(warehouse: JSONObject): MutableList<ProductCheck> = withContext(Dispatchers.IO) {
        val giWarehouseName = warehouse.getString("giWarehouseName")
        val giWarehouseLocation = warehouse.getString("giWarehouseLocation")
        val warehouseName = warehouse.getString("warehouseName")
        val warehouseLocation = warehouse.getString("warehouseLocation")
        val balQty = warehouse.getString("balQty")
        val unitCode = warehouse.getString("unitCode")
        val price1 = warehouse.getString("price1")
        val price2 = warehouse.getString("price2")
        val price221 = warehouse.getString("price221")
        val price222 = warehouse.getString("price222")
        val disc26 = warehouse.getString("disc26")
        val item = ProductCheck(
            giWarehouseName,
            giWarehouseLocation,
            warehouseName,
            warehouseLocation,
            balQty.toFloat(),
            unitCode,
            price1.toFloat(),
            price2.toFloat(),
            price221.toFloat(),
            price222.toFloat(),
            disc26
        )
        productCheckList.add(item)
        return@withContext productCheckList
    }

    suspend fun clearGiWarehouseFromList(): MutableList<ProductCheck> = withContext(Dispatchers.IO){
        productCheckList.clear()
        return@withContext productCheckList
    }

}