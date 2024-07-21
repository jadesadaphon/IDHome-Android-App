package com.app.jade.dev.idhome.prasat.data.datasource

import org.json.JSONObject

class ProductSellRepository {

    private var productSellDataStorage = ProductSellDataStorage()
    suspend fun addProductsToSellList(jsonObject: JSONObject): MutableList<ProductSell> = productSellDataStorage.addProductsToSellList(jsonObject)

}