package com.example.donacionapp.feature_onboarding.presentation.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.donacionapp.R
import com.example.donacionapp.databinding.FragmentOnboardingFirstScreenBinding

class OnboardingFirstScreen : Fragment() {
    private var binding:FragmentOnboardingFirstScreenBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnboardingFirstScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}