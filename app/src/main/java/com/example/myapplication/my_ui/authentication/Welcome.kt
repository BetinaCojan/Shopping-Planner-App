package com.example.myapplication.my_ui.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Welcome : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        auth = Firebase.auth

        loginButton = findViewById(R.id.login_button_welcome)
        registerButton = findViewById(R.id.register_button_welcome)

        loginButton.setOnClickListener {
            val intent = Intent(
                this,
                Login::class.java
            )

            startActivity(intent)
        }

        registerButton.setOnClickListener {
            val intent = Intent(
                this,
                Register::class.java
            )

            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(
                this,
                MainActivity::class.java
            )

            startActivity(intent)
        }

        /*else{
            val intent = Intent(
                this,
                WelcomeActivity::class.java
            )

            startActivity(intent)
        }*/
    }
}