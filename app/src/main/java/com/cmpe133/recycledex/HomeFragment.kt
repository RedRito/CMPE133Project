package com.cmpe133.recycledex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_homepage.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(R.layout.fragment_homepage) {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var favArrayList: ArrayList<Centers>
    private lateinit var favoritecentersRecyclerView: RecyclerView
    private lateinit var bundle: Bundle

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
                    favoritecentersRecyclerView.adapter = adapter
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
        val rootView: View = layoutInflater.inflate(R.layout.fragment_centers, container, false)
        favoritecentersRecyclerView.layoutManager = LinearLayoutManager(context)
        favArrayList = arrayListOf<Centers>()
        auth = FirebaseAuth.getInstance()
        bundle = Bundle()
        var uid : String? = null
        if(auth.currentUser != null)
        {
            uid = auth.currentUser?.uid
            getUserFav(uid!!)

        }
        //MUST USE onClicks in onCreateView, otherwise it will register as NULL after the onCreate!!
        //
        learnto1.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.learnto1, PlasticsPageFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
        learnto2.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.learnto2, MetalsPageFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
        learnto3.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.learnto3, PaperPageFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
        learnto4.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.learnto4, GlassPageFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
        learnto5.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.learnto5, ElectronicsPageFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
        articleBoxHomePage.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.articleBoxHomePage, SearchingFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
        calculationbox.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.calculationbox, ProfileFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
        return rootView
    }
}