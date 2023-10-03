package com.example.donacionapp.feature_donors.data

import com.example.donacionapp.feature_donors.data.network.DonorService
import com.example.donacionapp.feature_donors.domain.Donors
import com.example.donacionapp.feature_donors.domain.DonorsRepository
import com.example.donacionapp.util.ResultState

class DonorsRepositoryImp(private val donorService: DonorService): DonorsRepository {
    override suspend fun getDonors(): ResultState<List<Donors>> = donorService.getDonor()
}