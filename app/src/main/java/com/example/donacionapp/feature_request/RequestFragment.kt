package com.example.donacionapp.feature_request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.donacionapp.R
import com.example.donacionapp.databinding.FragmentRequestBinding
import com.example.donacionapp.feature_donors.domain.Donors
import com.example.donacionapp.feature_donors.presentation.DonorsViewModel
import com.example.donacionapp.feature_pdfconvert.PdfConvert
import com.example.donacionapp.util.ResultState
import org.koin.androidx.viewmodel.ext.android.viewModel

class RequestFragment : Fragment() {
    private var binding : FragmentRequestBinding? = null
    private val viewModel : DonorsViewModel by viewModel()
    private lateinit var requestAdapter: RequestAdapter
    private var body = mutableListOf<Donors>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRequestBinding.inflate(inflater, container, false)
        binding?.toolbarRequest?.setOnClickListener {
            findNavController().navigate(R.id.action_requestFragment_to_homeFragment)
        }
        setRecyclerview()
        binding?.imageViewPdf2?.setOnClickListener {
            createPdf()
        }

        return binding?.root
    }
    private fun setRecyclerview(){
        viewModel.getDonors().observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is ResultState.Error -> binding?.progressBarRequest?.isVisible = false
                is ResultState.Loading -> binding?.progressBarRequest?.isVisible = true
                is ResultState.Success -> {
                    if(result.data.isEmpty()){
                        binding?.textViewAlert2?.isVisible = true
                        binding?.progressBarRequest?.isVisible = false
                        return@Observer
                    }else{
                        binding?.textViewAlert2?.isVisible = false
                    }
                    requestAdapter = RequestAdapter(result.data)
                    binding?.rvRequest?.adapter = requestAdapter
                    binding?.progressBarRequest?.isVisible = false
                    //val body = mutableListOf<Donors>()
                    body.addAll(result.data)
                }
            }
        })
    }
    private fun createPdf() {
        val pdfConver = PdfConvert()
        pdfConver.createPdf(requireContext(),body,requireActivity())
    }
}