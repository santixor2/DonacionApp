package com.example.donacionapp.feature_search.domain

import com.example.donacionapp.feature_donors.domain.Donors
import com.example.donacionapp.util.ResultState

interface SearchRepository {
    suspend fun getSearch(query : String): ResultState<List<Donors>>
}