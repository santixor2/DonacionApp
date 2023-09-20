package com.example.donacionapp.feature_onboarding.presentation.screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.donacionapp.R
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
            onBoardingFinished()
        }
        binding?.buttonSignup?.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingFragment_to_registerFragment)
            onBoardingFinished()
        }
        return binding?.root
    }
    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("onBoardingFinished", true)
        editor.apply()
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}