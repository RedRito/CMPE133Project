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
    private lateinit var database: DatabaseReference
    private lateinit var centersRecyclerView: RecyclerView          //THE RECYLER VIEW YOU EDIT / SET
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var centersArrayList: ArrayList<Centers>       //Array of type / object centers
    private lateinit var centersSearchedList: ArrayList<Centers>    //Array of type Centers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = layoutInflater.inflate(R.layout.fragment_centers, container, false)    //inflate your page here, goes in first argument, R.layout.YOUR_LAYOUT_NAME_HERE

        centersRecyclerView = rootView.findViewById(R.id.rvSuggestedCenters)    //get your recyclerview on the layout you inflated based on its ID
        centersRecyclerView.layoutManager = LinearLayoutManager(context)
        centersRecyclerView.setHasFixedSize(true)
        centersArrayList = arrayListOf<Centers>()   //initalize arrays
        centersSearchedList = arrayListOf<Centers>()

        firebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseAuth.currentUser?.uid!!
        var topText: TextView = rootView.findViewById(R.id.tvSuggestedRC)
        getArticleData()
        val queryText: SearchView = rootView.findViewById(R.id.locationsvFragment)
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



    private fun setArticleList(){
        val adapter = CentersFragmentAdapter(centersSearchedList)
        centersRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : CentersFragmentAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                //            val link = centersArrayList[position].link
//                            val i = Intent(Intent.ACTION_VIEW)
//                            i.data = Uri.parse(link)
//                            startActivity(i)
                Toast.makeText(context, centersArrayList[position].toString(), Toast.LENGTH_SHORT).show()

            }

        })
    }

    private fun getArticleData() {
        database = FirebaseDatabase.getInstance().getReference("Centers")   //Database reference (CHECK DATABASE TO MAKE SURE!!)

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
                    adapter.setOnItemClickListener(object : CentersFragmentAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            //YOUR CODE HERE
//                            val link = centersArrayList[position].link
//                            val i = Intent(Intent.ACTION_VIEW)
//                            i.data = Uri.parse(link)
//                            startActivity(i)

                            Toast.makeText(context, centersArrayList[position].toString(), Toast.LENGTH_SHORT).show()

                        }

                    })
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }


}