package com.example.projetblooddonation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {
    private var logon: Button? = null
    private var id: TextInputEditText? = null
    private var pwd: TextInputEditText? = null
    var Id: String? = null
    var PWD: String? = null
    private var register: TextView? = null
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        register = findViewById(R.id.register)
        logon = findViewById(R.id.modify)
        id = findViewById(R.id.Email)
        pwd = findViewById(R.id.bld)
        auth = FirebaseAuth.getInstance()
        register?.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@LoginPage,
                    SignUpPage::class.java
                )
            )
        })
        logon?.setOnClickListener(View.OnClickListener {
            Id = id?.getText().toString()
            PWD = pwd?.getText().toString()
            if (Id!!.isEmpty()) {
                id?.setError("Make sure to give your email !")
                id?.requestFocus()
                id?.setText("")
                pwd?.setText("")
            } else if (PWD!!.isEmpty()) {
                pwd?.setError("Make sure to type your password !")
                pwd?.requestFocus()
                id?.setText("")
                pwd?.setText("")
            } else {
                auth!!.signInWithEmailAndPassword(Id!!, PWD!!).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth!!.currentUser
                        Toast.makeText(
                            this@LoginPage,
                            "Authentication succeeded !",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(
                            Intent(
                                this@LoginPage,
                                MainActivity2::class.java
                            )
                        )
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginPage, "Authentication failed !",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }
}