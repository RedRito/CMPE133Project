package com.cmpe133.recycledex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.cmpe133.recycledex.databinding.ActivitySignInBinding
import com.cmpe133.recycledex.databinding.SignupMainBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class SignUpActivity : AppCompatActivity() {
    private  lateinit var  binding: SignupMainBinding
    private  lateinit var  database: DatabaseReference
    private  lateinit var  firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //inflate layout
        binding = SignupMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //intialize variables
        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

        //creates a user in the database given input
        fun createUser(email: String?) {
            val userId = firebaseAuth.currentUser?.uid
            val user = User(email,0.0, 0.0, 0.0, 0.0, 0.0, 0.0, arrayListOf<Centers>())
            //check if user is already in database
            if(userId != null)
            {
                database.child(userId).setValue(user).addOnCompleteListener {
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
                Toast.makeText(this, "Null User ID", Toast.LENGTH_SHORT).show()
            }
        }

        //clicking signup button
        binding.signupbutton.setOnClickListener{
            //get input
            val email = binding.usernameSignUpReal.text.toString().trim()
            val password = binding.passwordSignUpReal.text.toString().trim()
            val confirmPass = binding.cpasswordSignUpReal.text.toString().trim()
            //check input
            if(email.isNotEmpty() && password.isNotEmpty() && confirmPass.isNotEmpty())
            {
                if(password == confirmPass)
                {
                    //create user in database
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if(it.isSuccessful)
                        {
                            createUser(email)
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
                    Toast.makeText(this, "Passwords don't match ", Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                Toast.makeText(this, "Enter a username or password", Toast.LENGTH_SHORT).show()
            }
        }

    }
}