package com.example.cartrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class LauncherActivity : AppCompatActivity(),View.OnClickListener {
    private var register: Button? = null
    private var login: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        register = findViewById<View>(R.id.update) as Button
        login = findViewById<View>(R.id.SendEmail) as Button

        register!!.setOnClickListener(this)
        login!!.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.update -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.SendEmail -> {
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}