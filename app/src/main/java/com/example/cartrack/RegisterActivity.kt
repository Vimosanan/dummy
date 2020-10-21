package com.example.cartrack

import android.os.Bundle

class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setDark()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
}

