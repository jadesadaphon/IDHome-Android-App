package com.app.jade.dev.idhome.prasat.data.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject

class EkycViewModel: ViewModel() {

    val networkType: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val serverAddress: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val userData : MutableLiveData<JSONObject> by lazy {
        MutableLiveData<JSONObject>()
    }

    val apiResponse: MutableLiveData<JSONObject> by lazy {
        MutableLiveData<JSONObject>()
    }

}