package com.chaima.smartshop.data //productrepository avant modif

class ProductRepository(
    private val productDao: ProductDao,
    private val firestore: ProductFirestore,
    private val userId: String
) {
    // Flux en temps réel depuis Room
    fun getProductsFlow() = productDao.getAllProducts()

    // Ajouter un produit (local + cloud)
    suspend fun addProduct(product: ProductEntity) {
        productDao.insertProduct(product)
        firestore.saveProduct(product, userId)
    }

    // Mettre à jour
    suspend fun updateProduct(product: ProductEntity) {
        productDao.updateProduct(product)
        firestore.saveProduct(product, userId)
    }

    // Supprimer
    suspend fun deleteProduct(product: ProductEntity) {
        productDao.deleteProduct(product)
        firestore.deleteProduct(product.id, userId)
    }

    // Statistiques
    suspend fun getProductCount() = productDao.getProductCount()
    suspend fun getTotalStockValue() = productDao.getTotalStockValue()
}