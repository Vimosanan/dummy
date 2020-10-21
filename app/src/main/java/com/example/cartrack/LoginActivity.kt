package com.example.cartrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setDark()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}