package com.app.jade.dev.idhome.prasat.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.jade.dev.idhome.prasat.data.datasource.api.idhome.PoApi
import com.app.jade.dev.idhome.prasat.databinding.ActivityPoManageBinding
import com.app.jade.dev.idhome.prasat.ui.data.PoData
import com.app.jade.dev.idhome.prasat.ui.fragment.PoListFragment
import com.app.jade.dev.idhome.prasat.ui.viewmodel.PoManageActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PoManageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPoManageBinding

    private val viewModel: PoManageActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPoManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerView.id, PoListFragment()).commit()

        loadPo()
    }

    private fun loadPo() {
        val api = PoApi()
        CoroutineScope(Dispatchers.IO).launch {
            val result: MutableList<PoData>? = api.getData()
            result?.let {
                withContext(Dispatchers.Main) {
                    viewModel.updateListPo(it)
                }
            }
        }
    }


}
