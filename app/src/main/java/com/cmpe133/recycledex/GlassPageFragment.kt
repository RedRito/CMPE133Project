package com.cmpe133.recycledex

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmpe133.recycledex.databinding.FragmentGlassPageBinding
import com.cmpe133.recycledex.databinding.FragmentMetalsPageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GlassPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GlassPageFragment : Fragment() {
    private var _binding: FragmentGlassPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view = inflater!!.inflate(R.layout.fragment_profile, container, false)
        _binding = FragmentGlassPageBinding.inflate(inflater, container, false)

        //MUST USE onClicks in onCreateView, otherwise it will register as NULL after the onCreate!!
        binding.glassBackButton.setOnClickListener {

            val intent = Intent(context, HomePageActivity::class.java)
            startActivity(intent)
        }

        return binding.root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}