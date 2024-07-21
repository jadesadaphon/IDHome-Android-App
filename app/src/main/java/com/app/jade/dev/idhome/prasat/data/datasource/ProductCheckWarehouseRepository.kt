package com.app.jade.dev.idhome.prasat.data.datasource

import org.json.JSONObject

class ProductCheckWarehouseRepository {

    private var productWarehouseDataStorage = ProductCheckWarehouseDataStorage()
    suspend fun addProductCheckToList(warehouse: JSONObject): MutableList<ProductCheck> = productWarehouseDataStorage.addProductCheckList(warehouse)

    suspend fun clearProductCheckFromList(): MutableList<ProductCheck> = productWarehouseDataStorage.clearGiWarehouseFromList()

}