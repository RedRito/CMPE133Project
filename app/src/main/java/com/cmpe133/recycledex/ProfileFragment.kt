package com.cmpe133.recycledex

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.text.trimmedLength
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmpe133.recycledex.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.math.RoundingMode
import java.text.DecimalFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var favArrayList: ArrayList<Centers>
    private lateinit var centersRecyclerView: RecyclerView
    private lateinit var bundle: Bundle
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        database = FirebaseDatabase.getInstance().getReference("Users")
        if(!uid.isNullOrEmpty())
        {
            getUserData(uid)

        }
        else
        {
            Toast.makeText(context, "Log In To See Data", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onStart() {
        super.onStart()
        val uid = auth.currentUser?.uid
        database = FirebaseDatabase.getInstance().getReference("Users")
        if(!uid.isNullOrEmpty())
        {
            getUserData(uid)

        }
        else
        {
            binding.btnLogout.text = "LOGIN"
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun roundDown( num : Double) : String{
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        val round =  df.format(num)
        return round.toString()
    }


    private fun getUserData(userId: String) {

        database.child(userId).get().addOnSuccessListener {
            if(it.exists())
            {
                val email = it.child("userName").value
                val emiss = it.child("savedemissions").value
                val plastic = it.child("plasticSaved").value
                val elect = it.child("electSaved").value
                val paper = it.child("paperSaved").value
                val metal = it.child("metalSaved").value
                val glass = it.child("glassSaved").value
                var trimEmail = email.toString()
                if(email.toString().length > 10)
                {
                    val to = email.toString().indexOf('@')
                    trimEmail = if(to < 10) {
                        email.toString().substring(0, email.toString().indexOf('@')) + email.toString().substring(email.toString().indexOf('@'), email.toString().length)
                    } else {
                        email.toString().substring(0, 10) + email.toString().substring(email.toString().indexOf('@'), email.toString().length)
                    }
                }

                binding.tvEmail.text = trimEmail
                binding.tvTotalemiss.text = roundDown(emiss.toString().toDouble()) + " kg"
                binding.tvPlasticAmount.text = roundDown(plastic.toString().toDouble()) + " kg"
                binding.tvWoodAmount.text = roundDown(paper.toString().toDouble()) + " kg"
                binding.tvMetalAmount.text = roundDown(metal.toString().toDouble()) + " kg"
                binding.tvElectAmount.text = roundDown(elect.toString().toDouble()) + " kg"
                binding.tvClothesAmount.text = roundDown(glass.toString().toDouble()) + " kg"
            }
            else
            {
                Toast.makeText(context, "User does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(context, "Login To See Data", Toast.LENGTH_SHORT).show()
        }


    }

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
                    centersRecyclerView.adapter = adapter
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
        //val view = inflater!!.inflate(R.layout.fragment_profile, container, false)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        //MUST USE onClicks in onCreateView, otherwise it will register as NULL after the onCreate!!
        centersRecyclerView = binding.rcFavLocProfile
        centersRecyclerView.layoutManager = LinearLayoutManager(context)
        centersRecyclerView.setHasFixedSize(true)
        favArrayList = arrayListOf<Centers>()
        auth = FirebaseAuth.getInstance()
        bundle = Bundle()
        var uid : String? = null
        if(auth.currentUser != null)
        {
            uid = auth.currentUser?.uid
            getUserFav(uid!!)

        }
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
        binding.cvTotalemissions.setOnClickListener{
            val intent = Intent(context, EmissCalActivity::class.java)
            startActivity(intent)
        }
        return binding.root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}