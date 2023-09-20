package com.example.donacionapp.feature_donate.presentation

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.donacionapp.MainActivity
import com.example.donacionapp.R
import com.example.donacionapp.databinding.FragmentDonateBinding
import com.example.donacionapp.feature_donate.domain.Donor
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.android.get
import java.util.Date

class DonateFragment : Fragment() {
    private var binding : FragmentDonateBinding? = null
    private lateinit var donor : Donor
    private val db = get<FirebaseFirestore>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDonateBinding.inflate(inflater, container, false)
        (activity as MainActivity).showBottomNav(false)
        setAutocomplete()
        sendDonation()
        binding?.toolbarRequest?.setOnClickListener {
            findNavController().navigate(R.id.action_donateFragment4_to_homeFragment)
        }

        return binding?.root
    }
    private fun setAutocomplete(){
        val blood = resources.getStringArray(R.array.Blood)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdow_item, blood)
        binding?.autoCompleteBlood?.setAdapter(arrayAdapter)
        val city = resources.getStringArray(R.array.Citys)
        val cityAdapter = ArrayAdapter(requireContext(),R.layout.dropdow_item, city)
        binding?.autocompleteCity?.setAdapter(cityAdapter)
        val hospital = resources.getStringArray(R.array.Hospital)
        val hospitalAdapter = ArrayAdapter(requireContext(), R.layout.dropdow_item, hospital)
        binding?.autoCompleteHospital?.setAdapter(hospitalAdapter)
    }
    private fun sendDonation(){
        binding?.apply {
            buttonSend.setOnClickListener {
                donor = Donor(
                    autocompleteCity.text.toString(),
                    autoCompleteHospital.text.toString(),
                    autoCompleteBlood.text.toString(),
                    etPhone.text.toString(),
                    etNote.text.toString()
                )
                val verification = listOf(donor.city,donor.hospital,donor.blood,donor.phone,donor.note).any{it.isBlank()}
                if(verification){
                    snackBar("Por favor llene todos los campos")
                }else{
                    val user = FirebaseAuth.getInstance().currentUser
                    val dato = hashMapOf(
                        "city" to donor.city,
                        "hospital" to donor.hospital,
                        "blood" to donor.blood,
                        "name" to user?.displayName,
                        "phone" to donor.phone,
                        "note" to donor.note,
                        "created_at" to Timestamp(Date()).toDate(),
                        "image" to "https://firebasestorage.googleapis.com/v0/b/nocountrydonation.appspot.com/o/perfil-del-usuario.png?alt=media&token=39f35c98-cc1f-42de-8459-ad595e672d35",
                        "email" to user?.email
                    )
                    if (user != null) {
                        db.collection("donantes")
                            .document(user.uid)
                            .set(dato)
                            .addOnSuccessListener { documentReference ->
                                Log.d(
                                    ContentValues.TAG,
                                    "DocumentSnapshot added with ID: ${documentReference}"
                                )
                            }
                            .addOnFailureListener { e ->
                                Log.w(ContentValues.TAG, "Error adding document", e)
                            }
                    }
                    (activity as MainActivity).sendNotification("DonationApp","El usuario : ${user?.displayName} realizo una donacion","La donacion se realizo en el hospital: ${donor.hospital} \n tipo de sangre : ${donor.blood} \n nota : ${donor.note}")
                    snackBar("Se registro tu Donacion correctamente")
                    findNavController().navigate(R.id.action_donateFragment4_to_homeFragment)
                    FragmentSucessRequest().show(parentFragmentManager, "ventana")
                }
            }
        }
    }
    private fun snackBar(message: String) {
        binding?.frameLayout8?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    }
}