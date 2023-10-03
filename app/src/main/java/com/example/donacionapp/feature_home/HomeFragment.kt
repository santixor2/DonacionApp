package com.example.donacionapp.feature_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.donacionapp.MainActivity
import com.example.donacionapp.R
import com.example.donacionapp.databinding.FragmentHomeBinding
import com.example.donacionapp.feature_donors.presentation.DonorsViewModel
import com.example.donacionapp.util.ResultState
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var binding : FragmentHomeBinding? = null
    private val viewModel : DonorsViewModel by viewModel()
    private lateinit var homeAdapter: HomeAdapter
    private val auth = get<FirebaseAuth>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater , container,false)
        (activity as MainActivity).showBottomNav(true)
        setNavigation()
        setRecyclerview()
        setUser()
        return binding?.root
    }
    private fun setRecyclerview(){
        viewModel.getDonors().observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is ResultState.Error -> binding?.progressBarHome?.isVisible = false
                is ResultState.Loading -> binding?.progressBarHome?.isVisible = true
                is ResultState.Success -> {
                    if(result.data.isEmpty()){
                        binding?.textViewAlert?.isVisible = true
                        binding?.progressBarHome?.isVisible = false
                        return@Observer
                    }else{
                        binding?.textViewAlert?.isVisible = false
                    }
                    homeAdapter = HomeAdapter(result.data)
                    binding?.rvHome?.adapter = homeAdapter
                    binding?.progressBarHome?.isVisible = false
                }
            }
        })
    }
    private fun setNavigation(){
        binding?.buttonDonate?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_donateFragment42)
        }
//        binding?.buttonFindDonors?.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_donorsFragment)
//        }
        binding?.buttonRequest?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_requestFragment)
        }
//        binding?.imageViewPdf?.setOnClickListener {
//            RequestFragment().createPdf(requireContext(),requireActivity())
//        }
        binding?.include?.imageViewLogout?.setOnClickListener {
            (activity as MainActivity).finish()
        }
    }
    private fun setUser(){
        val user = auth.currentUser
        binding?.apply {
            include.textViewEmailHome.text = user?.email
            include.textViewNameHomes.text = user?.displayName.toString()
        }
    }

}