package com.example.cartrack

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cartrack.entitys.AppDatabase
import com.example.cartrack.entitys.UserOfApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(),View.OnClickListener {
    private var registerNow: TextView? = null
    private var password: EditText? = null
    private var email: EditText? = null
    private var login: Button? = null
    private var data:UserOfApp? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        registerNow = findViewById<View>(R.id.registerNow) as TextView
        password = findViewById<View>(R.id.Login_password ) as EditText
        email = findViewById<View>(R.id.reset_email) as EditText
        login = findViewById<View>(R.id.SendEmail) as Button


        registerNow!!.setOnClickListener(this)
        login!!.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.registerNow -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.SendEmail->{
                val login_Email: String = email?.text.toString()
                val pass: String = password?.text.toString()
                GlobalScope.launch {
                    val db = AppDatabase(this@LoginActivity)
                    data = db.usersOfAppDao.getSingleUser(login_Email,pass)
                    println(data)
                    if (data != null){
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        /*this@LoginActivity.email?.error = "Email address is Wrong"
                        this@LoginActivity.email?.requestFocus()*/
                    }
                }
              /*  if (data != null){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    email?.error = "Email address is Wrong"
                    email?.requestFocus()
                    password?.error = "Password is Wrong"
                    password?.requestFocus()
                }*/
            }
        }
    }
}