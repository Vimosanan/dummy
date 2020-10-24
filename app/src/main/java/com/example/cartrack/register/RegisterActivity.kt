package com.example.cartrack.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.cartrack.R
import com.example.cartrack.app.CartrackApplication
import com.example.cartrack.di.AppComponent
import com.example.cartrack.util.AppDatabase
import com.example.cartrack.entity.AppUser
import com.example.cartrack.login.LoginActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

class RegisterActivity : AppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {
    @Inject
    lateinit var db: AppDatabase

    private var register: Button? = null
    private var reg_uname: EditText? = null
    private var phone_number: EditText? = null
    private var post_Address: String? = null
    private var reg_password: EditText? = null
    private var confom_password: EditText? = null
    private var email: EditText? = null
    private var data: List<AppUser>? = null
    private var spinner: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = (applicationContext as CartrackApplication).appComponent
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        reg_uname = findViewById<View>(R.id.userName) as EditText
        phone_number = findViewById<View>(R.id.Up_Phone_number) as EditText
        //post_Address = findViewById<View>(R.id.Up_Post_Address) as EditText
        spinner = findViewById(R.id.Up_Post_AddressMain)
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


        spinner?.onItemSelectedListener = this

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
                val confP: String = confom_password?.text.toString()
                val hass: String = hashAndSavePasswordHash(reg_password?.text.toString())
                if (loginN.isEmpty()) {
                    reg_uname?.error = "Please Enter the Name"
                    reg_uname?.requestFocus()
                } else if (email_ad.isEmpty()) {
                    email?.error = "Please Enter the email address"
                    email?.requestFocus()
                } else if (phone_N.isEmpty()) {
                    phone_number?.error = "Please Enter the phone number"
                    phone_number?.requestFocus()
                } else if (pass.isEmpty()) {
                    reg_password?.error = "Please Enter the password"
                    reg_password?.requestFocus()
                } else if (confP.isEmpty()) {
                    confom_password?.error = "Please Enter the Conform Password"
                    confom_password?.requestFocus()
                } else if (loginN.isEmpty() && pass.isEmpty() && phone_N.isEmpty() && post_Address!!.isEmpty() && confP.isEmpty()) {
                    Toast.makeText(this, "Fields are empty", Toast.LENGTH_LONG).show()
                } else {
                    if (pass == confP) {
                        //saveData(loginN, phone_N, address, pass, email_ad)
                        GlobalScope.launch {
                            db.appUserDao()
                                .insert(AppUser(0, loginN, phone_N, post_Address, hass, email_ad))
                            data = db.appUserDao().allUserUsers
                            data?.forEach {
                                println(it)
                            }
                            if (data != null) {
                                val intent =
                                    Intent(this@RegisterActivity, LoginActivity::class.java)
                                startActivity(intent)
                            } else {
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

    fun hashAndSavePasswordHash(clearPassword: String): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(clearPassword.toByteArray(Charsets.UTF_8))
        val sb = StringBuilder()
        for (b in result) {
            sb.append(String.format("%02X", b))
        }
        val hashedPassword = sb.toString()
        return hashedPassword
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
        // An item was selected. You can retrieve the selected item using
        post_Address = parent?.getItemAtPosition(pos).toString()
    }
}

