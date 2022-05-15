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
import com.cmpe133.recycledex.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_profile.*

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

    private lateinit var user: User
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
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }


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
                    trimEmail = email.toString().substring(0, 10) + email.toString().substring(email.toString().indexOf('@'), email.toString().length)
                }
                binding.tvEmail.text = trimEmail
                binding.tvTotalemiss.text = emiss.toString() + " kg"
                binding.tvPlasticAmount.text = plastic.toString() + " kg"
                binding.tvWoodAmount.text = paper.toString() + " kg"
                binding.tvMetalAmount.text = metal.toString() + " kg"
                binding.tvElectAmount.text = elect.toString() + " kg"
                binding.tvClothesAmount.text = glass.toString() + " kg"
            }
            else
            {
                Toast.makeText(context, "User does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view = inflater!!.inflate(R.layout.fragment_profile, container, false)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val args = this.arguments
        val inputData = args?.get("rcname")
        //tvProfile.text = inputData.toString()
        Toast.makeText(context, inputData.toString(), Toast.LENGTH_SHORT).show()
        //MUST USE onClicks in onCreateView, otherwise it will register as NULL after the onCreate!!
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