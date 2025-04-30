package com.app.jade.dev.idhome.prasat.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.jade.dev.idhome.prasat.ui.data.PoData

class PoManageActivityViewModel: ViewModel() {

    private val _listPo = MutableLiveData<List<PoData>>(emptyList())
    val listPo: LiveData<List<PoData>> = _listPo

    fun updateListPo(update: MutableList<PoData>) {
        _listPo.value = update.toList()
    }
}