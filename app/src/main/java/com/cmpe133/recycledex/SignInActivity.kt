package com.cmpe133.recycledex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.cmpe133.recycledex.databinding.ActivitySignInBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    private  lateinit var  binding: ActivitySignInBinding
    private  lateinit var  database: DatabaseReference
    private  lateinit var  firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.loginButton.setOnClickListener{
            val email = binding.usernameSignInLayoutReal.text.toString().trim()
            val password = binding.passwordSignInLayoutReal.text.toString().trim()
            if(email.isNotEmpty() && password.isNotEmpty())
            {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful)
                    {
                        val intent = Intent(this, HomePageActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else
            {
                Toast.makeText(this, "Enter a username or password", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvForgotPassword.setOnClickListener {
            val intent = Intent(this@SignInActivity, ForgotPassActivity::class.java)
            startActivity(intent)

        }

    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null)
        {
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }
        val currentUser = firebaseAuth.currentUser
        val uid = currentUser?.uid.toString()
    }
}