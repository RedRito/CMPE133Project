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
        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        val searchFragment = SearchingFragment()
        val mapsFragment = MapsFragment()
        val centersFragment = CentersFragment()


        //first Fragment that is shown
        setCurrentFragment(homeFragment)
        bottomNavView.selectedItemId = R.id.home
        bottomNavView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home  -> setCurrentFragment(homeFragment)
                R.id.articles -> setCurrentFragment(searchFragment)
                R.id.search -> setCurrentFragment(centersFragment)
                R.id.map   -> setCurrentFragment(mapsFragment)
                R.id.profile -> setCurrentFragment(profileFragment)
            }
            true
        }









    }

    //sets the fragment to the one that is passed
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }


}