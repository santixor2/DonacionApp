package com.example.donacionapp.feature_onboarding.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.donacionapp.R
import com.example.donacionapp.databinding.FragmentOnboardingSecondScreenBinding


class OnboardingSecondScreen : Fragment() {
    private var binding: FragmentOnboardingSecondScreenBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnboardingSecondScreenBinding.inflate(inflater, container, false)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding?.buttonSecond?.setOnClickListener {
            viewPager?.currentItem = 2
        }
        return binding?.root
    }
    override fun onDestroy() {
        super.onDestroy()
        binding= null
    }
}