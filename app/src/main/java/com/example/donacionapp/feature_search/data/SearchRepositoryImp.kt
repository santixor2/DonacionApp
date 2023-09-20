package com.example.donacionapp.feature_search.data

import com.example.donacionapp.feature_donors.domain.Donors
import com.example.donacionapp.feature_search.data.network.SearchService
import com.example.donacionapp.feature_search.domain.SearchRepository
import com.example.donacionapp.util.ResultState

class SearchRepositoryImp(private val searchService: SearchService): SearchRepository {
    override suspend fun getSearch(query: String): ResultState<List<Donors>> = searchService.getSearch(query)
}