package com.cmpe133.recycledex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.login_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inflate layout
        setContentView(R.layout.login_main)
        //intialize variables
        val text: TextView = findViewById(R.id.tv_signup)
        val guest: TextView = findViewById(R.id.tv_guest)
        val login: TextView = findViewById(R.id.tv_login)
        //onclick signup = start signup acitivity
        text.setOnClickListener{
            val intent = Intent(this@MainActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
        //onclick guest = start homepage activity with no user
        guest.setOnClickListener{
            val intent = Intent(this@MainActivity, HomePageActivity::class.java)
            startActivity(intent)
        }
        //onclick login = start login acitivity
        login.setOnClickListener {
            val intent = Intent(this@MainActivity, SignInActivity::class.java)
            startActivity(intent)
        }

    }

}