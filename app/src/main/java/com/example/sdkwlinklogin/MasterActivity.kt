package com.example.sdkwlinklogin

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import dalvik.system.ZipPathValidator.setCallback
import np.com.geniussystems.wlinklogin.lib.SingleSignOn
import np.com.geniussystems.wlinklogin.lib.SingleSignOnCallback

class MasterActivity : AppCompatActivity(), SingleSignOnCallback {
    private var sso: SingleSignOn? = null
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_master)

        tvResult = findViewById(R.id.tvResult)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        // Initialize the SingleSignOn
        try {
            sso = SingleSignOn(
                this,
                enableSMSLogin = true,
                enableQRLogin = true,
                appId = "63GRuqwwXb",
                clientIdentifier = "freshchat",
            )
            sso?.init() // <- IMPORTANT
        } catch (e: Exception) {
            Log.e("SSO", "Failed to initialize SingleSignOn: ${e.message}")
        }

        btnLogin.setOnClickListener {
            Log.e("SSO login", "Login button clicked")
            sso?.login()
        }
    }

    // SingleSignOn callback
    override fun onLoginSuccess(token: String?, username: String?) {
        Log.i("SSO Token", token ?: "No Token")
        Log.i("SSO Username", username ?: "No Username")
        tvResult.text = "Login Success!\nUsername: $username\nToken: $token"

        val phone = sso?.getCellNumber() ?: ""
        Log.i("Phone", phone)
    }

    override fun onLoginError(message: String?) {
        Log.e("SSO Error", message ?: "Unknown error")
        tvResult.text = "Login Error: $message"
    }

    override fun onDestroy() {
        super.onDestroy()
        sso?.deinit()
    }
}
