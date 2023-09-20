package com.example.donacionapp.feature_donors.domain

import com.example.donacionapp.util.ResultState

interface DonorsRepository {
    suspend fun getDonors(): ResultState<List<Donors>>
}