package com.example.donacionapp.feature_authentication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.donacionapp.MainActivity
import com.example.donacionapp.R
import com.example.donacionapp.databinding.FragmentLoginBinding
import com.example.donacionapp.feature_authentication.login.domain.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.get

class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    private val auth = get<FirebaseAuth>()
    private lateinit var user: User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        (activity as MainActivity).showBottomNav(false)
        login()
        binding?.textViewSendRegister?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        return binding?.root
    }

    private fun login() {
        binding?.apply {
            buttonLogin2.setOnClickListener {
                user = User(edEmail.text.toString(), edPassword.text.toString())
                val verification = listOf(user.email, user.password).any { it.isBlank() }
                if (verification) {
                    snackBar("Por favor llene todos los campos")
                } else {
                    auth.signInWithEmailAndPassword(user.email, user.password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                findNavController().navigate(R.id.action_loginFragment_to_permissionLocationFragment)
                                snackBar("Bienvenido: ${user.email}")
                            }
                        }.addOnFailureListener {
                            snackBar("Usuario no Creado")
                        }
                }
            }
        }
    }

    private fun snackBar(message: String) {
        binding?.frameLayout5?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}