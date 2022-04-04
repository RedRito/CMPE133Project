package com.example.simpleapptodolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)
        val text: TextView = findViewById(R.id.tv_signup)
        val guest: TextView = findViewById(R.id.tv_guest)
        val login: TextView = findViewById(R.id.tv_login)
        text.setOnClickListener{
            val intent = Intent(this@MainActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
        guest.setOnClickListener{
            val intent = Intent(this@MainActivity, HomePageActivity::class.java)
            startActivity(intent)
        }
        login.setOnClickListener {
            val intent = Intent(this@MainActivity, SignInActivity::class.java)
            startActivity(intent)
        }

    }

}