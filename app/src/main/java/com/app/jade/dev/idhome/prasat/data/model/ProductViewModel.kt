package com.app.jade.dev.idhome.prasat.data.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.jade.dev.idhome.prasat.data.datasource.ProductCheck
import com.app.jade.dev.idhome.prasat.data.datasource.ProductCheckWarehouseRepository
import com.app.jade.dev.idhome.prasat.data.datasource.ProductSell
import com.app.jade.dev.idhome.prasat.data.datasource.ProductSellRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class ProductViewModel: ViewModel(){

    val eventName: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val barCode: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    var productCheckList = MutableLiveData<MutableList<ProductCheck>>()
    var productSellList = MutableLiveData<MutableList<ProductSell>>()
    private var productCheckWarehouseRepository = ProductCheckWarehouseRepository()
    private var productSellRepository = ProductSellRepository()

    fun updateBarCode(string: String){
        barCode.value = string
        CoroutineScope(Dispatchers.Main).launch {
            productCheckWarehouseRepository.clearProductCheckFromList()
        }
    }

    fun addProductsToSellList(jsonObject: JSONObject) {
        CoroutineScope(Dispatchers.Main).launch {
            productSellList.value = productSellRepository.addProductsToSellList(jsonObject)
        }
    }

    fun addProductCheckToList(warehouse: JSONObject) {
        CoroutineScope(Dispatchers.Main).launch {
            productCheckList.value = productCheckWarehouseRepository.addProductCheckToList(warehouse)
        }
    }
}