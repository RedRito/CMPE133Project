package com.cmpe133.recycledex

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_articles.view.*

class LocationFragment {
    private var mImageResource: Int = 0
    private var mText1: String? = null
    private lateinit var database: DatabaseReference
    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var articleArrayList: ArrayList<Locations>
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLocationData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = layoutInflater.inflate(R.layout.homepage_main, container, false)
        // reference to the recycler view id
        articleRecyclerView = rootView.findViewById(R.id.rvlocation)
        articleRecyclerView.layoutManager = LinearLayoutManager(context)
        articleRecyclerView.setHasFixedSize(true)
        locationArrayList = arrayListOf<Location>()
        return rootView
    }

    private fun getLocationData() {
        database = FirebaseDatabase.getInstance().getReference("Centers")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ArticleSnapshot in snapshot.children) {

                        val article = ArticleSnapshot.getValue(Center::class.java)

                        locationArrayList.add(article!!)

                    }

                    val adapter = LocationFragmentAdapter(locationArrayList)
                    articleRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object :
                        LocationFragmentAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            val link = locationArrayList[position].link
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(link)
                            startActivity(i)
                            //Toast.makeText(context, articleArrayList[position].toString(), Toast.LENGTH_SHORT).show()

                        }

                    })
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        companion object {
            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment SearchingFragment.
             */
            @JvmStatic
            fun newInstance(param1: String, param2: String) =
                LocationFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
        }
    }