package com.example.donacionapp.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val appModule= module {
    single {
        FirebaseAuth.getInstance()
    }
    single {
        Firebase.firestore
    }
}