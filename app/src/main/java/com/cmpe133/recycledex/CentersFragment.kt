package com.cmpe133.recycledex

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class CentersFragment : Fragment() {
    private lateinit var database: DatabaseReference                //Database
    private lateinit var centersRecyclerView: RecyclerView          //Recyclerview
    private lateinit var firebaseAuth: FirebaseAuth                 //Firebase auth
    private lateinit var centersArrayList: ArrayList<Centers>       //Array of type / object centers
    private lateinit var centersSearchedList: ArrayList<Centers>    //Array of type Centers
    private lateinit var bundle: Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = layoutInflater.inflate(R.layout.fragment_centers, container, false)    //inflate your page here, goes in first argument, R.layout.YOUR_LAYOUT_NAME_HERE

        centersRecyclerView = rootView.findViewById(R.id.rvSuggestedCenters)    //get your recyclerview on the layout you inflated based on its ID
        centersRecyclerView.layoutManager = LinearLayoutManager(context)        //intialize recyclerview
        centersRecyclerView.setHasFixedSize(true)
        centersArrayList = arrayListOf<Centers>()   //initalize arrays
        centersSearchedList = arrayListOf<Centers>()
        bundle = Bundle()


        var topText: TextView = rootView.findViewById(R.id.tvSuggestedRC)
        //Gets the article data to set into the recyclerview
        getCenterData()
        //For searching
        val queryText: SearchView = rootView.findViewById(R.id.locationsvFragment)
        //when a query is entered in the keyboard, gets the search results from the list, sets to recyclerview
        queryText.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                queryText.clearFocus()

                getQuery(query!!)
                topText.text = "Results"
                setArticleList()


                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        return rootView
    }
    //gets the query from the list
    //sets into a intiaized list
    private fun getQuery(query : String){
        centersSearchedList.clear()
        for(center: Centers in centersArrayList)
        {
            val name = center.name.lowercase()
            val desc = center.description.lowercase()
            val phone = center.phone.lowercase()
            val loc = center.location.lowercase()
            val query = query.lowercase()
            if(name.contains(query) || desc.contains(query) || phone.contains(query) || loc.contains(query))
            {
                centersSearchedList.add(center)
            }
        }
    }


    //sets the list of items into the recyclerview
    private fun setArticleList(){
        val adapter = CentersFragmentAdapter(centersSearchedList)
        centersRecyclerView.adapter = adapter

        //when clicked on an item, brings them to the maps fragment page
        adapter.setOnItemClickListener(object : CentersFragmentAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val rcname = centersArrayList[position].name
                val location = centersArrayList[position].location
                val hours = centersArrayList[position].description
                val phone = centersArrayList[position].phone
                val materials = centersArrayList[position].accepted
                val link = centersArrayList[position].link
                val lat = centersArrayList[position].lat
                val lon = centersArrayList[position].lon
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

    //gets the center data from the database
    //sets the intialized list of centers
    private fun getCenterData() {
        database = FirebaseDatabase.getInstance().getReference("Centers")   //Database reference

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (CenterSnapshot in snapshot.children) {
                        val center = CenterSnapshot.getValue(Centers::class.java)
                        centersArrayList.add(center!!)

                    }
                    val adapter = CentersFragmentAdapter(centersArrayList)
                    centersRecyclerView.adapter = adapter
                    //when you click on a card, do something
                    //passes data to fragment maps
                    adapter.setOnItemClickListener(object : CentersFragmentAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val rcname = centersArrayList[position].name
                            val location = centersArrayList[position].location
                            val hours = centersArrayList[position].description
                            val phone = centersArrayList[position].phone
                            val materials = centersArrayList[position].accepted
                            val link = centersArrayList[position].link
                            val lat = centersArrayList[position].lat
                            val lon = centersArrayList[position].lon

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


}