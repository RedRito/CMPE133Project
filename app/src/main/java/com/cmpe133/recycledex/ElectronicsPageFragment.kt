package com.cmpe133.recycledex

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmpe133.recycledex.databinding.FragmentElectronicsPageBinding
import com.cmpe133.recycledex.databinding.FragmentMetalsPageBinding


class ElectronicsPageFragment : Fragment() {
    private var _binding: FragmentElectronicsPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentElectronicsPageBinding.inflate(inflater, container, false)

        //MUST USE onClicks in onCreateView, otherwise it will register as NULL after the onCreate!!
        binding.ElectronicbackButton.setOnClickListener {

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