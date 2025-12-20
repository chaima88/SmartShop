package com.chaima.smartshop.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String = "",
    val name: String,
    val quantity: Int,
    val price: Double,
    val imageUrl: String = "" // <-- Nouveau champ

)