package com.example.cartrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class LauncherActivity : AppCompatActivity(),View.OnClickListener {
    private var register: Button? = null
    private var login: Button? = null
    private lateinit var sharedpre:SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedpre = SharedPref(this)
        if (sharedpre.loadLoginSharedPrefState()){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish();
        }else{
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_launcher)
        }

        register = this.findViewById<View>(R.id.update) as Button
        login = this.findViewById<View>(R.id.SendEmail) as Button

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