package com.example.sdkwlinklogin

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import np.com.geniussystems.wlinklogin.lib.SingleSignOn
import np.com.geniussystems.wlinklogin.lib.SingleSignOnCallback

class MasterActivity : AppCompatActivity(), SingleSignOnCallback {
    private var sso: SingleSignOn? = null
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_master)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }

        tvResult = findViewById(R.id.tvResult)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        // Initialize the SingleSignOn
        try {
            sso = SingleSignOn(
                this,
                enableSMSLogin = true,
                enableQRLogin = true,
                appId = "63GRuqwwXb",
                clientIdentifier = "quick-connect",
            )
            sso?.init()
            sso?.setOnSingleSignOnCallback(this)


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
        tvResult.text = "Login Error: $message ---- end"
    }

    override fun onDestroy() {
        super.onDestroy()
        sso?.deinit()
    }
}
