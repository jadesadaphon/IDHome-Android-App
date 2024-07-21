package com.app.jade.dev.idhome.prasat.data.datasource

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class TransferDataStorage {

    private companion object {
        private const val TAG = "TransferDataStorage"
    }

    private val transferList = ArrayList<Transfer>()

    suspend fun addTransferToList(jsonObject: JSONObject): MutableList<Transfer> = withContext(Dispatchers.IO) {

        for (key in jsonObject.keys()) {
            val item = jsonObject.getJSONObject(key)
            val personNameTh = item.getString("mynameth")
            val remark = item.getString("remark")
            val sysCreate = item.getString("syscreate")
            val sysUpdate = item.getString("sysupdate")
            val products = item.getJSONObject("products")

            Log.d(TAG, products.toString())

            val transferListItem = Transfer(key, personNameTh, remark, sysCreate, sysUpdate,products)
            transferList.add(transferListItem)
        }



        return@withContext transferList
    }
}