package com.example.donacionapp.feature_onboarding.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.donacionapp.databinding.FragmentOnboardingBinding
import com.example.donacionapp.feature_onboarding.presentation.screen.OnboardingFirstScreen
import com.example.donacionapp.feature_onboarding.presentation.screen.OnboardingSecondScreen
import com.example.donacionapp.feature_onboarding.presentation.screen.OnboardingThirdScreen

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
        setupOnboarding()
        return binding?.root
    }
    private fun setupOnboarding(){
        val fragmentList = arrayListOf(
            OnboardingFirstScreen(),
            OnboardingSecondScreen(),
            OnboardingThirdScreen()
        )
        val adapter = ViewPagerAdapter(fragmentList,requireActivity().supportFragmentManager,lifecycle)
        binding?.viewPager?.adapter = adapter
        binding?.viewPager?.let {
            binding?.dots?.attachTo(
                it
            )
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}