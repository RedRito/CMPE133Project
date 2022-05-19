package com.cmpe133.recycledex

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmpe133.recycledex.databinding.FragmentGlassPageBinding
import com.cmpe133.recycledex.databinding.FragmentMetalsPageBinding

class GlassPageFragment : Fragment() {
    private var _binding: FragmentGlassPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGlassPageBinding.inflate(inflater, container, false)

        //onclick = start activity
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