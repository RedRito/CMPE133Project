package com.example.simpleapptodolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simpleapptodolist.databinding.ActivityHomePageBinding

class HomePageActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}