package com.app.jade.dev.idhome.prasat.data.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.jade.dev.idhome.prasat.data.datasource.Transfer
import com.app.jade.dev.idhome.prasat.data.datasource.TransferRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class TransferViewModel : ViewModel(){

    private var transferRepository = TransferRepository()
    var transferList = MutableLiveData<MutableList<Transfer>>()

    fun addTransferToList(jsonObject: JSONObject) {
        CoroutineScope(Dispatchers.Main).launch {
            transferList.value = transferRepository.addTransferToList(jsonObject)
        }
    }

}