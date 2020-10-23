package com.example.cartrack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.cartrack.entitys.AppDatabase
import com.example.cartrack.entitys.UserOfApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity(),View.OnClickListener, AdapterView.OnItemSelectedListener {
    private var register: Button? = null
    private var reg_uname: EditText? = null
    private var phone_number:EditText? = null
    private var post_Address:EditText? = null
    private var reg_password:EditText? = null
    private var confom_password:EditText? = null
    private var email:EditText? = null
    private var data:List<UserOfApp>? = null
    private var spinner: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        reg_uname = findViewById<View>(R.id.userName) as EditText
        phone_number = findViewById<View>(R.id.Up_Phone_number) as EditText
        //post_Address = findViewById<View>(R.id.Up_Post_Address) as EditText
        spinner = findViewById<View>(R.id.Up_Post_AddressMain) as Spinner
        reg_password = findViewById<View>(R.id.Up_password) as EditText
        confom_password = findViewById<View>(R.id.Up_Confom_password) as EditText
        email = findViewById<View>(R.id.Up_email) as EditText
        register = findViewById<View>(R.id.update) as Button
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.country_arrays,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner!!.adapter = adapter
        }

        register!!.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.update -> {
               // Toast.makeText(this, "clicked", Toast.LENGTH_LONG).show()
                val loginN: String = reg_uname?.text.toString()
                val pass: String = reg_password?.text.toString()
                val phone_N: String = phone_number?.text.toString()
                val email_ad: String = email?.text.toString()
                val address: String = post_Address?.text.toString()
                val confP: String = confom_password?.text.toString()
                if (loginN.isEmpty()) {
                    reg_uname?.error = "Please Enter the Name"
                    reg_uname?.requestFocus()
                } else if (email_ad.isEmpty()) {
                    email?.error = "Please Enter the email address"
                    email?.requestFocus()
                } else if (phone_N.isEmpty()) {
                    phone_number?.error = "Please Enter the phone number"
                    phone_number?.requestFocus()
                } else if (address.isEmpty()) {
                    post_Address?.error = "Please Enter the Address"
                    post_Address?.requestFocus()
                } else if (pass.isEmpty()) {
                    reg_password?.error = "Please Enter the password"
                    reg_password?.requestFocus()
                } else if (confP.isEmpty()) {
                    confom_password?.error = "Please Enter the Conform Password"
                    confom_password?.requestFocus()
                } else if (loginN.isEmpty() && pass.isEmpty() && phone_N.isEmpty() && address.isEmpty() && confP.isEmpty()) {
                    Toast.makeText(this, "Fields are empty", Toast.LENGTH_LONG).show()
                } else {
                    if (pass == confP) {
                        //saveData(loginN, phone_N, address, pass, email_ad)
                        GlobalScope.launch {
                            val db = AppDatabase(this@RegisterActivity)
                            db.usersOfAppDao.insert(UserOfApp(1,loginN,phone_N,address,pass,email_ad))
                            data = db.usersOfAppDao.allUsers
                            data?.forEach {
                                println(it)
                            }
                            
                            if (data != null) {
                                startActivity(intent)
                                val intent =
                                    Intent(this@RegisterActivity, LoginActivity::class.java)
                            }
                            else{
                                email?.error = "Email address is Wrong"
                                email?.requestFocus()
                            }
                        }

                        confom_password?.error = "Passwords are not Matched"
                    } else {
                    confom_password?.requestFocus()
                    }
                }
            }
        }
    }
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3
: Long) {
        TODO("Not yet implemented")
        val spinner: Spinner = findViewById(R.id.Up_Post_AddressMain)
        spinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}

