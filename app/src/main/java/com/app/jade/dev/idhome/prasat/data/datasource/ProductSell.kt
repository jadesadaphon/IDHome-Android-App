package com.app.jade.dev.idhome.prasat.data.datasource

import org.json.JSONArray

data class ProductSell(
    var barCode:String,
    var productCode:String,
    var productName:String,
    var amount:Int,
    val warehouse: JSONArray,
)
