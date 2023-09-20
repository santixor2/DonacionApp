package com.example.donacionapp.feature_search.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donacionapp.feature_donors.domain.Donors
import com.example.donacionapp.feature_search.domain.SearchRepository
import com.example.donacionapp.util.ResultState
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository): ViewModel() {
    private val _search = MutableLiveData<UiState>(UiState.Loading)
    var search : LiveData<UiState> = _search

      fun getSearch(query : String){
          viewModelScope.launch {
              when(val result = searchRepository.getSearch(query)){
                  is ResultState.Error -> Log.d("SearchViewModel", "error Food")
                  is ResultState.Loading -> Log.d("SearchViewModel", "error2 Food")
                  is ResultState.Success -> {_search.value = UiState.Success(result.data)}
              }
          }
      }

//    fun getSearch(query : String) = liveData(Dispatchers.IO){
//        emit(ResultState.Loading())
//        try {
//            emit(searchRepository.getSearch(query)
//
//        }catch (e:Exception){
//            emit(ResultState.Error(e))
//        }
//    }
}
sealed class UiState {
    data class Success(val items: List<Donors>) : UiState()
    object Loading : UiState()
    data class Error(val msg: String) : UiState()
}