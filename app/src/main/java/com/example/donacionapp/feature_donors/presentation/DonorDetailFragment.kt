package com.example.donacionapp.feature_donors.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.donacionapp.R
import com.example.donacionapp.databinding.FragmentDonorDetailBinding
import com.example.donacionapp.feature_donors.domain.Donors
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DonorDetailFragment : BottomSheetDialogFragment() {
    private var binding: FragmentDonorDetailBinding? = null
    private val args: DonorDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDonorDetailBinding.inflate(inflater, container, false)
        //(activity as MainActivity).showBottomNav(false)
        binding?.include2?.cardView2?.setBackgroundResource(R.drawable.rounded)
        bindDonorsDetail(args.detail)
        //setNavigation()
        return binding?.root
    }

    private fun bindDonorsDetail(donors: Donors) {
        binding?.apply {
            include2.textViewDonorsName.text = donors.name
            include2.textViewCity.text = donors.city
            include2.textViewBloodType.text = donors.blood
            include2.textViewHospitals.text = donors.hospital
            include2.textViewEmails.text = donors.email
            //Glide.with(root.context).load(donors.image).into(imageViewProfile)
            include2.imageViewProfile.load(donors.image) {
                placeholder(R.drawable.error)
                crossfade(true)
                crossfade(300)
            }
        }

    }

//    private fun setNavigation() {
////        binding?.buttonPermission?.setOnClickListener {
////            (activity as MainActivity).showPermission()
////        }
////        binding?.buttonShowMaps?.setOnClickListener {
////            findNavController().navigate(R.id.action_donorDetailFragment_to_mapsFragment)
////        }
//        binding?.toolbarRequest?.setOnClickListener {
//            findNavController().navigate(R.id.action_donorDetailFragment_to_searchFragment)
//        }
//    }

}