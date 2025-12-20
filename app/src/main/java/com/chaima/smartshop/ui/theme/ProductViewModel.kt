package com.chaima.smartshop.ui.theme //productviewmodel avant modif

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaima.smartshop.data.ProductDatabase
import com.chaima.smartshop.data.ProductEntity
import com.chaima.smartshop.data.ProductRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(context: Context) : ViewModel() {

    private val userId = FirebaseAuth.getInstance().currentUser?.uid
        ?: error("Utilisateur non authentifié")

    private val productDao = ProductDatabase.getDatabase(context).productDao()
    private val firestore = com.chaima.smartshop.data.ProductFirestore()
    private val repository = ProductRepository(productDao, firestore, userId)

    val products: StateFlow<List<ProductEntity>> = repository.getProductsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val stats = products.map { list ->
        val count = list.size
        val total = list.sumOf { it.price * it.quantity }
        Stats(count, total)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Stats(0, 0.0))

    fun addProduct(product: ProductEntity) = viewModelScope.launch {
        repository.addProduct(product)
    }

    fun updateProduct(product: ProductEntity) = viewModelScope.launch {
        repository.updateProduct(product)
    }

    fun deleteProduct(product: ProductEntity) = viewModelScope.launch {
        repository.deleteProduct(product)
    }

    data class Stats(val count: Int, val totalValue: Double)
}