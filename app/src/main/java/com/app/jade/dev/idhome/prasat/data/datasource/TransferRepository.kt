package com.app.jade.dev.idhome.prasat.data.datasource

import org.json.JSONObject

class TransferRepository {

    private var transferDataStorage = TransferDataStorage()
    suspend fun addTransferToList(jsonObject: JSONObject): MutableList<Transfer> = transferDataStorage.addTransferToList(jsonObject)

}