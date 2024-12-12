package com.example.shoppingproto.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Product
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class HomeScreenUiEvents {
    data object Loading : HomeScreenUiEvents()
    data class Success(val featured: List<Product>, val popularProducts: List<Product>) : HomeScreenUiEvents()
    data class Error(val message: String) : HomeScreenUiEvents()
}

class HomeViewModel(private val getProductUseCase: GetProductUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUiEvents>(HomeScreenUiEvents.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getAllProducts()
    }

    private fun getAllProducts(){
        viewModelScope.launch{
            _uiState.value = HomeScreenUiEvents.Loading
            val featured = getProducts("electronics")
            val popularProducts = getProducts("men's clothing")
            if (featured.isEmpty() || popularProducts.isEmpty()){
                _uiState.value = HomeScreenUiEvents.Error("Failed to load products")
                return@launch
            }
            _uiState.value = HomeScreenUiEvents.Success(featured,popularProducts)
        }
    }


    private suspend fun getProducts(category: String?):List<Product> {
        getProductUseCase.execute(category).let { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    return result.value
                }
                is ResultWrapper.Failure -> {
                    return emptyList()
                }
            }
        }
    }
}
