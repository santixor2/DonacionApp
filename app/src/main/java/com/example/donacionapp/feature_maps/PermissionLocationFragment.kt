package com.example.donacionapp.feature_maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.donacionapp.MainActivity
import com.example.donacionapp.R
import com.example.donacionapp.databinding.FragmentPermissionLocationBinding

class PermissionLocationFragment : Fragment() {
    private var binding : FragmentPermissionLocationBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPermissionLocationBinding.inflate(inflater, container, false)
        binding?.buttonPermissionLocation?.setOnClickListener {
            (activity as MainActivity).showPermission()
            binding?.buttonContinue?.isVisible = true
        }
        binding?.buttonContinue?.setOnClickListener {
            findNavController().navigate(R.id.action_permissionLocationFragment_to_homeFragment)
        }
        return binding?.root
    }

}