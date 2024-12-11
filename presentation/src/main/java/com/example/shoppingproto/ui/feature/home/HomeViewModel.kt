package com.example.shoppingproto.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Product
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class HomeScreenUiEvents{
    data object Loading: HomeScreenUiEvents()
    data class Success(val data: List<Product>): HomeScreenUiEvents()
    data class Error(val message: String): HomeScreenUiEvents()
}

class HomeViewModel(private val getProductUseCase: GetProductUseCase): ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUiEvents>(HomeScreenUiEvents.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getProducts()
    }
    private fun getProducts(){
        viewModelScope.launch {
            _uiState.value = HomeScreenUiEvents.Loading
            getProductUseCase.execute().let { result ->
                when(result){
                    is ResultWrapper.Success -> {
                        val data = result.value
                        _uiState.value = HomeScreenUiEvents.Success(data)
                    }
                    is ResultWrapper.Failure -> {
                        val error = result.exception.message?: "Error occurred"
                        _uiState.value = HomeScreenUiEvents.Error(error)
                    }
                }
            }
        }
    }

}
