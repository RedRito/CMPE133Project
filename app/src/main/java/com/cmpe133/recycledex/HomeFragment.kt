package com.cmpe133.recycledex

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_homepage.*


class HomeFragment : Fragment(R.layout.fragment_homepage) {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var favArrayList: ArrayList<Centers>
    private lateinit var favcentersRecyclerView: RecyclerView
    private lateinit var bundle: Bundle


    //gets an array list of user's favorite recycling centers
    private fun getUserFav(uid: String)
    {
        favArrayList.clear()
        database = FirebaseDatabase.getInstance().getReference("Users")

        database.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    for(centers in snapshot.child("favCenters").children)
                    {
                        val center = centers.getValue(Centers::class.java)
                        favArrayList.add(center!!)
                    }
                    val adapter = CentersFragmentAdapter(favArrayList)
                    favcentersRecyclerView.adapter = adapter
                    //on card click = send data to maps fragment
                    adapter.setOnItemClickListener(object : CentersFragmentAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val rcname = favArrayList[position].name
                            val location = favArrayList[position].location
                            val hours = favArrayList[position].description
                            val phone = favArrayList[position].phone
                            val materials = favArrayList[position].accepted
                            val link = favArrayList[position].link
                            val lat = favArrayList[position].lat
                            val lon = favArrayList[position].lon

                            bundle.putString("rcname", rcname)
                            bundle.putString("location", location)
                            bundle.putString("hours", hours)
                            bundle.putString("phone", phone)
                            bundle.putString("materials", materials)
                            bundle.putString("link", link)
                            bundle.putDouble("lat", lat)
                            bundle.putDouble("lon", lon)
                            val fragment = MapsFragment()
                            fragment.arguments = bundle
                            fragmentManager?.beginTransaction()?.replace(R.id.flFragment, fragment)?.commit()
                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //inflate layout
        val rootView: View = layoutInflater.inflate(R.layout.fragment_homepage, container, false)

        //intialize variables
        favcentersRecyclerView = rootView.findViewById(R.id.rcFavLocHome)
        favcentersRecyclerView.layoutManager = LinearLayoutManager(context)
        favArrayList = arrayListOf<Centers>()
        auth = FirebaseAuth.getInstance()
        bundle = Bundle()
        var uid : String? = null
        //check if user logged in
        if(auth.currentUser != null)
        {
            uid = auth.currentUser?.uid
            getUserFav(uid!!)

        }

        //Intialize all cards in horizontal scroll view
        //Sets onclick to start activity of that card's info
        var learnto1: ImageView = rootView.findViewById(R.id.learnto1)
        learnto1.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, PlasticsPageFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
        var learnto2: ImageView = rootView.findViewById(R.id.learnto2)
        learnto2.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, MetalsPageFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
        var learnto3: ImageView = rootView.findViewById(R.id.learnto3)
        learnto3.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, PaperPageFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
        var learnto4: ImageView = rootView.findViewById(R.id.learnto4)
        learnto4.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, GlassPageFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
        var learnto5: ImageView = rootView.findViewById(R.id.learnto5)
        learnto5.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, ElectronicsPageFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }

        //Card of top suggested article
        var articleBoxHomePage: CardView = rootView.findViewById(R.id.cvsuggesteditem)
        articleBoxHomePage.setOnClickListener {
            val link = "https://www.recyclenow.com/how-to-recycle/how-is-plastic-recycled"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(link)
            startActivity(i)
        }
        //Card of emissions => goes to profile page
        var calculationbox: CardView = rootView.findViewById(R.id.cv_totalemissions)
        calculationbox.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, ProfileFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
        return rootView
    }
}
