package com.example.healthc.presentation.food.product.search_product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.food.SearchFoodProduct
import com.example.healthc.domain.repository.FoodRepository
import com.example.healthc.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchProductViewModel @Inject constructor(
    private val repository : FoodRepository
) : ViewModel() {
    private val _searchProductUiEvent = MutableStateFlow<SearchProductUiEvent>(
        SearchProductUiEvent.Unit)
    val searchProductUiEvent : StateFlow<SearchProductUiEvent>
        get() = _searchProductUiEvent

    private val _productName = MutableLiveData<String>("")
    val productName : LiveData<String> get() = _productName

    fun setProduct(productName: String){
        _productName.value = productName
    }

    fun getFoodIngredient(){
        viewModelScope.launch {
            val searchResult = repository.searchFoodProduct(
                requireNotNull(_productName.value)
            )
            when(searchResult){
                is Resource.Success -> {
                    _searchProductUiEvent.value =
                        SearchProductUiEvent.Success(searchResult.result)
                }

                is Resource.Failure -> {
                    _searchProductUiEvent.value = SearchProductUiEvent.Failure
                }

                is Resource.Loading -> { }
            }
        }
    }

    sealed class SearchProductUiEvent {
        data class Success(val foodIngredient: SearchFoodProduct) : SearchProductUiEvent()
        object Failure : SearchProductUiEvent()
        object Unit : SearchProductUiEvent()
    }
}