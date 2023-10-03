package com.example.donacionapp.di.module

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import com.example.donacionapp.feature_donors.data.DonorsRepositoryImp
import com.example.donacionapp.feature_donors.domain.DonorsRepository
import com.example.donacionapp.feature_donors.data.network.DonorService
import com.example.donacionapp.feature_donors.presentation.DonorsViewModel
import com.example.donacionapp.feature_search.domain.SearchRepository
import com.example.donacionapp.feature_search.data.SearchRepositoryImp
import com.example.donacionapp.feature_search.data.network.SearchService
import com.example.donacionapp.feature_search.presentation.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind

val donorsModule = module {

    factoryOf(::DonorsRepositoryImp){bind<DonorsRepository>()}
    factoryOf(::DonorService)
    //viewModelOf(::DonorsViewModel)
    viewModelOf(::DonorsViewModel)

    factoryOf(::SearchRepositoryImp){bind<SearchRepository>()}
    factoryOf(::SearchService)
    viewModelOf(::SearchViewModel)

}