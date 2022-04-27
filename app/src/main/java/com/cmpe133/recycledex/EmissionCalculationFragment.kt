package com.cmpe133.recycledex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.cmpe133.recycledex.databinding.FragmentEmissionCalculationBinding
import com.cmpe133.recycledex.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmissionCalculationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmissionCalculationFragment : Fragment() {
    private var _binding: FragmentEmissionCalculationBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var user: User
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        database = FirebaseDatabase.getInstance().getReference("Users")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentEmissionCalculationBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}