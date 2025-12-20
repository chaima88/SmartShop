package com.chaima.smartshop.data //productfirestore avant modif

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class ProductFirestore {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("products")

    suspend fun saveProduct(product: ProductEntity, userId: String) {
        collection.document(userId).collection("items").document(product.id)
            .set(product)
            .await()
    }

    suspend fun deleteProduct(productId: String, userId: String) {
        collection.document(userId).collection("items").document(productId)
            .delete()
            .await()
    }

    fun observeProducts(userId: String) = collection
        .document(userId)
        .collection("items")
        .addSnapshotListener { snapshot, _ ->
            // Géré via repository
        }
}