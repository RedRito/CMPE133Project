package com.cmpe133.recycledex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.cmpe133.recycledex.databinding.ActivityEmissCalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EmissCalActivity : AppCompatActivity() {
    private  lateinit var  binding: ActivityEmissCalBinding
    private  lateinit var  database: DatabaseReference
    private  lateinit var  firebaseAuth: FirebaseAuth
    private  lateinit var  uid: String
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmissCalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        val currentUser = firebaseAuth.currentUser
        uid = currentUser?.uid.toString()

        val items = resources.getStringArray(R.array.mats_array)
        val inputChoice = resources.getStringArray(R.array.choice_array)
        var choice: String? = ""
        var inputed: String? = ""
        if(binding.spChoice != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
            binding.spChoice.adapter = adapter

            binding.spChoice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    choice = items[pos].toString()
                    Toast.makeText(this@EmissCalActivity, items[pos].toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    choice = null
                }
            }
        }
        if(binding.spWeightAmount != null)
        {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, inputChoice)
            binding.spWeightAmount.adapter = adapter

            binding.spWeightAmount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    inputed = inputChoice[pos].toString()
                    Toast.makeText(this@EmissCalActivity, inputChoice[pos].toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    inputed = null
                }
            }
        }
        binding.btnSubmitAmount.setOnClickListener {
            when {
                inputed.equals("Weight") -> {
                    var amountString: String = binding.names.text.toString().trim()
                    if(amountString.isNullOrBlank())
                    {
                        Toast.makeText(this@EmissCalActivity, "Incorrect input", Toast.LENGTH_SHORT).show()

                    }
                    else
                    {
                        var amount = amountString.toDouble()

                        when(choice){
                            "Plastic" -> plasticW(amount)
                            "Paper" -> paperW(amount)
                            "Metal" -> metalW(amount)
                            "Electronics" -> electW(amount)
                            "Glass" -> glassW(amount)
                            else -> Toast.makeText(this@EmissCalActivity, "Incorrect input", Toast.LENGTH_SHORT).show()
                        }
                    }


                }
                inputed.equals("Amount") -> {
                    var amountString: String = binding.names.text.toString().trim()
                    if(amountString.isNullOrBlank())
                    {
                        Toast.makeText(this@EmissCalActivity, "Incorrect input", Toast.LENGTH_SHORT).show()

                    }
                    else
                    {
                        var amount = amountString.toDouble()
                        when(choice)
                        {
                            "Plastic" -> plasticA(amount)
                            "Paper" -> paperA(amount)
                            "Metal" -> metalA(amount)
                            "Electronics" -> electA(amount)
                            "Glass" -> glassA(amount)
                            else -> Toast.makeText(this@EmissCalActivity, "Incorrect input", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else -> {
                    Toast.makeText(this@EmissCalActivity, "Incorrect input", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
    private fun plasticW(weight: Double){
        if(weight < 1)
        {
            Toast.makeText(this@EmissCalActivity, "invalid amount", Toast.LENGTH_SHORT).show()
            return
        }
        // 1kg of plastic = X kg amount of carbon emission
        val plastic = 1.7
        var total = plastic * weight
        updateUserData("plasticSaved",total)
    }
    private fun paperW(weight: Double){
        if(weight < 1)
        {
            Toast.makeText(this@EmissCalActivity, "invalid amount", Toast.LENGTH_SHORT).show()
            return
        }
        var paper = .5
        var total = paper * weight
        updateUserData("paperSaved",total)

    }
    private fun metalW(weight: Double){
        if(weight < 1)
        {
            Toast.makeText(this@EmissCalActivity, "invalid amount", Toast.LENGTH_SHORT).show()
            return
        }
        var metal = 2.0
        var total = metal * weight
        updateUserData("metalSaved",total)
    }
    private fun electW(weight: Double){
        if(weight < 1)
        {
            Toast.makeText(this@EmissCalActivity, "invalid amount", Toast.LENGTH_SHORT).show()
            return
        }
        var elect = 10.0
        var total = elect * weight
        updateUserData("electSaved",total)

    }
    private fun glassW(weight: Double){
        if(weight < 1)
        {
            Toast.makeText(this@EmissCalActivity, "invalid amount", Toast.LENGTH_SHORT).show()
            return
        }
        var glass = 3.0
        var total = glass * weight
        updateUserData("glassSaved",total)
    }


    private fun plasticA(number: Double){
        if(number < 1)
        {
            Toast.makeText(this@EmissCalActivity, "invalid amount", Toast.LENGTH_SHORT).show()
            return
        }
        // 1 plastic bag = x kg amount of carbon emission
        val plastic = .5
        var total = plastic * number
        updateUserData("plasticSaved",total)

    }
    private fun paperA(number: Double){
        if(number < 1)
        {
            Toast.makeText(this@EmissCalActivity, "invalid amount", Toast.LENGTH_SHORT).show()
            return
        }
        var paper = .5
        var total = paper * number
        updateUserData("paperSaved",total)

    }
    private fun metalA(number: Double){
        if(number < 1)
        {
            Toast.makeText(this@EmissCalActivity, "invalid amount", Toast.LENGTH_SHORT).show()
            return
        }
        var metal = 2.0
        var total = metal * number
        updateUserData("metalSaved",total)

    }
    private fun electA(number: Double){
        if(number < 1)
        {
            Toast.makeText(this@EmissCalActivity, "invalid amount", Toast.LENGTH_SHORT).show()
            return
        }
        var elect = 10.0
        var total = elect * number
        updateUserData("electSaved",total)

    }
    private fun glassA(number: Double){
        if(number < 1)
        {
            Toast.makeText(this@EmissCalActivity, "invalid amount", Toast.LENGTH_SHORT).show()
            return
        }
        var glass = 3.0
        var total = glass * number
        updateUserData("glassSaved",total)

    }
    private fun updateUserData(type : String, value: Double){
        database = FirebaseDatabase.getInstance().getReference("Users")


        fun mapAndUpdate(emiss: Double, typeValue: Double){
            var sum = 0.0
            //Log.println(Log.ASSERT, "typeValue before", typeValue.toString())
            //Log.println(Log.ASSERT, "Value before", value.toString())

            sum = typeValue + value
            var emission = emiss + value
            //Log.println(Log.ASSERT, "emiss after", emission.toString())

            val user = mapOf<String, Double>(
                "savedemissions" to emission,
                type to sum
            )
            database.child(uid).updateChildren(user).addOnSuccessListener {
                Toast.makeText(this@EmissCalActivity, "Success!!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this@EmissCalActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        }


        database.child(uid).get().addOnSuccessListener {
            val emiss = it.child("savedemissions").value.toString().toDouble()
            val plastic = it.child("plasticSaved").value.toString().toDouble()
            val elect = it.child("electSaved").value.toString().toDouble()
            val paper = it.child("paperSaved").value.toString().toDouble()
            val metal = it.child("metalSaved").value.toString().toDouble()
            val glass = it.child("glassSaved").value.toString().toDouble()
            when(type)
            {
                "plasticSaved" -> {
                    mapAndUpdate(emiss, plastic)
                }
                "paperSaved" -> {
                    mapAndUpdate(emiss, paper)
                }
                "metalSaved" -> {
                    mapAndUpdate(emiss, metal)
                }
                "electSaved" -> {
                    mapAndUpdate(emiss, elect)
                }
                "glassSaved" -> {
                    mapAndUpdate(emiss, glass)
                }
                else -> Toast.makeText(this@EmissCalActivity, "Incorrect input", Toast.LENGTH_SHORT).show()

            }
        }








    }


    }





