package com.example.donacionapp.feature_donors.presentation

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
import com.example.donacionapp.databinding.FragmentDonorsBinding
import com.example.donacionapp.feature_donors.domain.Donors
import com.example.donacionapp.util.ResultState
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class DonorsFragment : Fragment(), DonorsAdapter.OnDonorClicked {
    private var binding : FragmentDonorsBinding? = null
    private val db = get<FirebaseFirestore>()
    private lateinit var donorsAdapter : DonorsAdapter
    private val viewModel : DonorsViewModel by viewModel()
    private lateinit var list : List<Donors>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDonorsBinding.inflate(inflater, container, false)
        (activity as MainActivity).showBottomNav(false)
        binding?.toolbarRequest?.setOnClickListener {
            findNavController().navigate(R.id.action_donorsFragment_to_homeFragment)
        }
        setRecyclerview()
        return binding?.root
    }
    private fun setRecyclerview(){
        viewModel.getDonors().observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is ResultState.Error -> binding?.progressBarDonors?.isVisible = false
                is ResultState.Loading -> binding?.progressBarDonors?.isVisible = true
                is ResultState.Success -> {
                    if(result.data.isEmpty()){
                        binding?.textView8Alert?.isVisible = true
                        binding?.progressBarDonors?.isVisible = false
                        return@Observer
                    }else{
                        binding?.textView8Alert?.isVisible = false
                    }
                    donorsAdapter = DonorsAdapter(result.data,this)
                    binding?.rvDonors?.adapter = donorsAdapter
                    binding?.progressBarDonors?.isVisible = false
                }
            }
        })
    }
    override fun OnDonorClickListener(donors: Donors, position: Int) {
        val action = DonorsFragmentDirections.actionDonorsFragmentToDonorDetailFragment(donors)
        findNavController().navigate(action)
    }
//    private fun setListener(){
//        binding?.searchView?.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                TODO("Not yet implemented")
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                TODO("Not yet implemented")
//            }
//        })
//    }

//    private fun filterList(q : String?){
//        if(q != null ){
//            val filtered = mutableListOf<Donors>()
//            for(i in list ){
//                if(i.name.lowercase(Locale.ROOT).contains(q))
//                    filtered.add(i)
//
//            }
//            if(filtered.isEmpty()){
//                Toast.makeText(requireContext(),"No Data",Toast.LENGTH_SHORT).show()
//            }
//            else{
//                donorsAdapter.set
//            }
//        }
//    }

}