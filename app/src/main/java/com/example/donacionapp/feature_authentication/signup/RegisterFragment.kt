package com.example.donacionapp.feature_authentication.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.donacionapp.MainActivity
import com.example.donacionapp.R
import com.example.donacionapp.databinding.FragmentRegisterBinding
import com.example.donacionapp.feature_authentication.signup.domain.NewUser
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import org.koin.android.ext.android.get

class RegisterFragment : Fragment() {
    private var binding: FragmentRegisterBinding? = null
    private lateinit var user: NewUser
    private val auth = get<FirebaseAuth>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        (activity as MainActivity).showBottomNav(false)
        signup()
        return binding?.root
    }

    private fun signup() {
        binding?.apply {
            buttonRegister.setOnClickListener {
                user = NewUser(
                    etEmail.text.toString(),
                    etPass.text.toString(),
                    editTextName.text.toString()
                )
                val verification = listOf(user.email, user.password, user.name).any { it.isBlank() }
                if (verification) {
                    snackBar("Por favor llene todos los campos")
                } else {
                    auth.createUserWithEmailAndPassword(user.email, user.password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                findNavController().navigate(R.id.action_registerFragment_to_permissionLocationFragment)
                                snackBar("Bienvenido: ${user.email}")
                                val users = auth.currentUser
                                val displayname = users?.displayName
                                if(displayname == null ){
                                    val profileChangeRequest = UserProfileChangeRequest.Builder()
                                        .setDisplayName(user.name)
                                        .build()
                                    users?.updateProfile(profileChangeRequest)
                                }
                            }
                        }.addOnFailureListener {
                            snackBar("Hubo un error intenta nuevamente")
                        }
                }
            }
        }
    }

    private fun snackBar(message: String) {
        binding?.frameLayout6?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    }

}