package com.app.jade.dev.idhome.prasat.data.datasource

import org.json.JSONObject

data class Transfer(
    val transferCode:String,
    val personNameTh:String,
    val remark:String,
    val sysCreate:String,
    val sysUpdate:String,
    val products: JSONObject
)
