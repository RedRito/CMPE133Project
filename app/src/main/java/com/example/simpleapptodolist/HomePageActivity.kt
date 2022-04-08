package com.example.simpleapptodolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.simpleapptodolist.databinding.ActivityHomePageBinding
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePageActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //create a variable for each Fragment
        val articlesFragment = ArticlesFragment()
        val homeFragment = HomeFragment()


        //first Fragment that is shown
        setCurrentFragment(articlesFragment)

        bottomNavView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home  -> setCurrentFragment(homeFragment)
                R.id.profile -> setCurrentFragment(articlesFragment)
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }


}