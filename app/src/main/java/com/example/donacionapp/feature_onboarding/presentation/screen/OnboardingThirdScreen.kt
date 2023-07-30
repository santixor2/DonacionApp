package com.example.donacionapp.feature_onboarding.presentation.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.donacionapp.R
import com.example.donacionapp.databinding.FragmentOnboardingSecondScreenBinding
import com.example.donacionapp.databinding.FragmentOnboardingthirdScreenBinding

class OnboardingThirdScreen : Fragment() {
    private var binding: FragmentOnboardingthirdScreenBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnboardingthirdScreenBinding.inflate(inflater, container, false)
        binding?.buttonLogin?.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingFragment_to_loginFragment)
        }
        return binding?.root
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}