package com.app.jade.dev.idhome.prasat.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.jade.dev.idhome.prasat.databinding.ActivityMainBinding
import com.app.jade.dev.idhome.prasat.ui.fragment.MainFragment
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"
    private var lastBackPressedTime: Long = 0
    private var backPressedCount = 0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.hasExtra("userData")) {
            val userData = intent.getStringExtra("userData")?.let { JSONObject(it) }
            Toast.makeText(this, userData?.getString("name"), Toast.LENGTH_SHORT).show()
        }
        initBinding()
        initView()
    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(binding.mainActivityFragmentContainerView.id, MainFragment()).commit()
    }

    private fun initView() {

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()

        if (currentTime - lastBackPressedTime > 2000) {
            backPressedCount = 1
        } else {
            backPressedCount++
            if (backPressedCount == 2) {
                finishAffinity()
                super.onBackPressed()
            }
        }
        lastBackPressedTime = currentTime
    }



}
