package com.example.projetblooddonation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.projetblooddonation.LoginPage
import com.example.projetblooddonation.MainActivity2
import com.example.projetblooddonation.R
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val handler = Handler()
        handler.postDelayed({
            if (FirebaseAuth.getInstance().currentUser == null) {
                startActivity(
                    Intent(
                        this@SplashScreen,
                        LoginPage::class.java
                    )
                )
                finish()
            } else {
                startActivity(
                    Intent(
                        this@SplashScreen,
                        MainActivity2::class.java
                    )
                )
                finish()
            }
        }, 2000)
    }
}