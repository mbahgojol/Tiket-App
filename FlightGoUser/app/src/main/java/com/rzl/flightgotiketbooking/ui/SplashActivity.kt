package com.rzl.flightgotiketbooking.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.rzl.flightgotiketbooking.R
import com.rzl.flightgotiketbooking.data.local.SharedPref
import com.rzl.flightgotiketbooking.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.myLooper()!!).postDelayed({
            CoroutineScope(Dispatchers.IO).launch {
                sharedPref.getToken().collect {
                    if (it.isNullOrEmpty()) {
                        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }, 5000)
    }
}