package com.app.jade.dev.idhome.prasat.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.app.jade.dev.idhome.prasat.R
import com.app.jade.dev.idhome.prasat.data.model.EkycViewModel
import com.app.jade.dev.idhome.prasat.databinding.ActivityEkycSplashScreenBinding
import com.app.jade.dev.idhome.prasat.networkmonitor.ConnectivityReceiver
import com.app.jade.dev.idhome.prasat.data.datasource.api.idhome.UpdateTokenFcmApi
import com.app.jade.dev.idhome.prasat.ui.fragment.LoginFragment
import com.app.jade.dev.idhome.prasat.ui.fragment.SplashScreenFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.play.core.integrity.IntegrityManagerFactory
import com.google.android.play.core.integrity.IntegrityTokenRequest
import com.google.android.play.core.integrity.IntegrityTokenResponse
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import org.jose4j.jwe.JsonWebEncryption
import org.jose4j.jws.JsonWebSignature
import org.jose4j.jwx.JsonWebStructure
import org.json.JSONObject
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import kotlin.math.floor

@SuppressLint("CustomSplashScreen")
class EkycActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "EkycActivity"
        private lateinit var binding: ActivityEkycSplashScreenBinding
        private lateinit var broadcastReceiver: BroadcastReceiver
        private lateinit var updateToken: UpdateTokenFcmApi
    }

    private val viewModel : EkycViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission(),) { isGranted: Boolean ->
        if (!isGranted) {
            Toast.makeText(this ,getString(R.string.please_enable_notifications), Toast.LENGTH_SHORT).show()
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun getDevice(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(TAG, msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }

    private fun registerNetworkBroadcastReceiver(){
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun unregisteredNetwork(){
        try {
            unregisterReceiver(broadcastReceiver)
        }catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    private fun replaceLoginSplashScreenFragment() {
        supportFragmentManager.beginTransaction().replace(binding.ekycSplashScreenActivityFragmentContainerView.id, SplashScreenFragment()).commit()
    }

    private fun replaceLoginFragment() {
        Handler(Looper.getMainLooper()).postDelayed({
            supportFragmentManager.beginTransaction().replace(binding.ekycSplashScreenActivityFragmentContainerView.id, LoginFragment()).commit()
        }, 2000)
    }

    private fun openMainActivity(userData: JSONObject) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("userData", userData.toString())
        startActivity(intent)
        finish()
    }

    private fun viewEvent(){
        askNotificationPermission()
        replaceLoginSplashScreenFragment()
        replaceLoginFragment()
    }

    private fun liveDataObserve(){
        val userDataObserver = Observer<JSONObject> { obj ->
            val sharedPreferences = getSharedPreferences("FCM_PREF", Context.MODE_PRIVATE)
            val newToken = sharedPreferences.getString("fcm_token", null)
            if (newToken != null) {
                lifecycleScope.launch {
                    try {
                        updateToken.updateTokenFCM(obj.getString("code"), newToken)
                        openMainActivity(obj)
                    } catch (e: Exception) {
                        Log.e(TAG, "Error updating token: ${e.message}")
                    }
                }
            }
        }
        viewModel.userData.observe(this, userDataObserver)
    }

    private fun getToken() {
        val decryptionKey = generateDecryptionKey()
        val verificationKey = generateVerificationKey()
        val nonce: String = generateNonce()
        // Create an instance of a manager.
        val integrityManager =
            IntegrityManagerFactory.create(this)
        // Request the integrity token by providing a nonce.
        val integrityTokenResponse: Task<IntegrityTokenResponse> = integrityManager.requestIntegrityToken(
            IntegrityTokenRequest.builder()
                .setNonce(nonce)
                .build())
        integrityTokenResponse.addOnSuccessListener { integrityTokenResponse1 ->
            val integrityToken: String = integrityTokenResponse1.token()
            Log.w(TAG, "integrityToken:="+integrityToken)
            decryptToken(integrityToken,decryptionKey,verificationKey)
        }
        integrityTokenResponse.addOnFailureListener { e ->
            Log.w(TAG, "integrityToken error :="+e)
        }
    }

    /** โค้ดส่วนนี้ต้องอยู่ในแบ็กเอนด์ Server ถึงจะถูกต้อง*/
    private fun decryptToken(integrityToken: String, decryptionKey: String, verificationKey:String){
        // base64OfEncodedDecryptionKey is provided through Play Console.
        val decryptionKeyBytes: ByteArray = Base64.decode(decryptionKey, Base64.DEFAULT)
        // Deserialized encryption (symmetric) key.
        val mDecryptionKey: SecretKey = SecretKeySpec(decryptionKeyBytes,0, decryptionKeyBytes.size, "AES")
        // base64OfEncodedVerificationKey is provided through Play Console.
        val encodedVerificationKey: ByteArray = Base64.decode(verificationKey, Base64.DEFAULT)
        // Deserialized verification (public) key.
        try {
            val mVerificationKey = KeyFactory.getInstance("EC").generatePublic(X509EncodedKeySpec(encodedVerificationKey))
            val jwe: JsonWebEncryption = JsonWebStructure.fromCompactSerialization(integrityToken) as JsonWebEncryption
            jwe.setKey(mDecryptionKey)
            // This also decrypts the JWE token.
            val compactJws: String = jwe.payload
            val jws: JsonWebSignature = JsonWebStructure.fromCompactSerialization(compactJws) as JsonWebSignature
            jws.setKey(mVerificationKey)
            // This also verifies the signature.
            val payload: String = jws.getPayload()
        }catch (e : InvalidKeySpecException) {
            Log.e(TAG, e.message.toString())
        }catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, e.message.toString())
        }
    }

    private fun generateNonce(): String {
        val length = 50
        var nonceKey = ""
        val allowed = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        (0 until length).forEach { i ->
            nonceKey += allowed[floor(Math.random() * allowed.length).toInt()].toString()
        }
        return nonceKey
    }

    /** โค้ดส่วนนี้ต้องอยู่ในแบ็กเอนด์ Server ถึงจะถูกต้อง*/
    private fun generateDecryptionKey(): String {
        val length = 50
        var  decryptionKey = ""
        val allowed = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        (0 until length).forEach { i ->
            decryptionKey += allowed[floor(Math.random() * allowed.length).toInt()].toString()
        }
        val encodedString = Base64.encodeToString(decryptionKey.toByteArray(), Base64.DEFAULT)
        Log.d(TAG, encodedString)
        return encodedString
    }

    /** โค้ดส่วนนี้ต้องอยู่ในแบ็กเอนด์ Server ถึงจะถูกต้อง*/
    private fun generateVerificationKey(): String {
        val length = 50
        var verificationKey = ""
        val allowed = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        (0 until length).forEach { i ->
            verificationKey += allowed[floor(Math.random() * allowed.length).toInt()].toString()
        }
        val encodedString = Base64.encodeToString(verificationKey.toByteArray(), Base64.DEFAULT)
        Log.d(TAG, encodedString)
        return encodedString
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEkycSplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        FirebaseApp.initializeApp(this)
        broadcastReceiver = ConnectivityReceiver(viewModel)
        updateToken = UpdateTokenFcmApi()
        registerNetworkBroadcastReceiver()
        liveDataObserve()
        getToken()
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG,"onStart")
        viewEvent()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisteredNetwork()
    }




}

