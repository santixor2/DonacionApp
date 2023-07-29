package com.example.donacionapp.feature_onboarding.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.donacionapp.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {
    private var binding: FragmentOnboardingBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        binding?.viewPager?.let {
            binding?.dots?.attachTo(
                it
            )
        }
        return binding?.root
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}