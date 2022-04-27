package com.cmpe133.recycledex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.cmpe133.recycledex.databinding.ActivityEmissCalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EmissCalActivity : AppCompatActivity() {
    private  lateinit var  binding: ActivityEmissCalBinding
    private  lateinit var  database: DatabaseReference
    private  lateinit var  firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmissCalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

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
        if(choice.isNullOrBlank() || inputed.isNullOrBlank())
        {
            Toast.makeText(this@EmissCalActivity, "No Input", Toast.LENGTH_SHORT).show()
        }
        else
        {
            binding.btnSubmitAmount.setOnClickListener {
                when {
                    inputed.equals("Weight") -> {
                        var amountString: String = binding.names.text.toString().trim()
                        var amount = amountString.toDouble()

                        when(choice){
                            "Plastic" -> plasticW(amount)
                            "Paper" -> print("hello")
                            "Metal" -> print("hello")
                            "Electronics" -> print("hello")
                            "Glass" -> print("hello")
                            else -> Toast.makeText(this@EmissCalActivity, "Incorrect input", Toast.LENGTH_SHORT).show()
                        }

                    }
                    inputed.equals("Amount") -> {
                        var amountString: String = binding.names.text.toString().trim()
                        var amount = amountString.toDouble()
                        when(choice)
                        {
                            "Plastic" -> plasticA(amount)
                            "Paper" -> print("hello")
                            "Metal" -> print("hello")
                            "Electronics" -> print("hello")
                            "Glass" -> print("hello")
                            else -> Toast.makeText(this@EmissCalActivity, "Incorrect input", Toast.LENGTH_SHORT).show()
                        }

                    }
                    else -> {
                        Toast.makeText(this@EmissCalActivity, "Incorrect input", Toast.LENGTH_SHORT).show()
                    }
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



    }
    }
    private fun paperW(){

    }
    private fun metalW(){

    }
    private fun electW(){

    }
    private fun glassW(){

    }


    private fun plasticA(number: Double){
        if(number < 1)
        {

            return
        }
        // 1 plastic bag = x kg amount of carbon emission
        val plastic = .5
        var total = plastic * number

    }
    private fun paperA(){

    }
    private fun metalA(){

    }
    private fun electA(){

    }
    private fun glassA(){

    }




