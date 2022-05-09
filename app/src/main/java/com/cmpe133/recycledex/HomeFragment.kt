package com.cmpe133.recycledex

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cmpe133.recycledex.databinding.FragmentHomeBinding
import com.cmpe133.recycledex.databinding.FragmentMetalsPageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view = inflater!!.inflate(R.layout.fragment_profile, container, false)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        //MUST USE onClicks in onCreateView, otherwise it will register as NULL after the onCreate!!
        binding.electronicsCard.setOnClickListener {

            //val intent = Intent(context, ElectronicsPageFragment::class.java)
            //startActivity(intent)
        }

        return binding.root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}