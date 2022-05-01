package com.cmpe133.recycledex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.cmpe133.recycledex.databinding.ActivityForgotPassBinding
import com.cmpe133.recycledex.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_forgot_pass.*

class ForgotPassActivity : AppCompatActivity() {
    private  lateinit var  binding: ActivityForgotPassBinding
    private  lateinit var  database: DatabaseReference
    private  lateinit var  firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnSubmit2.setOnClickListener {
            val email = binding.tvEmailFillIn.text.toString().trim()
            if(email.isNotEmpty())
            {
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener{
                    task -> if(task.isSuccessful){
                    Toast.makeText(this, "Email sent successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else
                {
                    Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                }
                }
            }
            else
            {
                Toast.makeText(this, "Cannot enter empty text", Toast.LENGTH_SHORT).show()
            }
        }


    }
}