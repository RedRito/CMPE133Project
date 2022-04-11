package com.cmpe133.recycledex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.cmpe133.recycledex.databinding.ActivityHomePageBinding
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
        //val searchFragment = SearchFragment()


        //first Fragment that is shown
        setCurrentFragment(articlesFragment)

        bottomNavView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home  -> setCurrentFragment(homeFragment)
                R.id.profile -> setCurrentFragment(articlesFragment)
                //R.id.search -> setCurrentFragment(searchFragment)
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