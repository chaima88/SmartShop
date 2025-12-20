package com.chaima.smartshop.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.chaima.smartshop.data.ProductEntity
import java.util.*

@Composable
fun ProductFormDialog(
    product: ProductEntity? = null,
    onDismiss: () -> Unit,
    onSave: (ProductEntity) -> Unit
) {
    var name by remember { mutableStateOf(product?.name ?: "") }
    var quantity by remember { mutableStateOf((product?.quantity ?: 0).toString()) }
    var price by remember { mutableStateOf((product?.price ?: 0.0).toString()) }
    var imageUrl by remember { mutableStateOf(product?.imageUrl ?: "") } // ← Nouveau champ
    var error by remember { mutableStateOf("") }

    val isEditMode = product != null

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isEditMode) "Modifier le produit" else "Ajouter un produit") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                if (error.isNotEmpty()) {
                    Text(error, color = MaterialTheme.colorScheme.error)
                }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nom du produit") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantité") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Prix (DT)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("URL de l'image (optionnel)") },
                    placeholder = { Text("https://exemple.com/image.jpg") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Validation
                    if (name.isBlank()) {
                        error = "Le nom est requis"
                        return@Button
                    }
                    val q = quantity.toIntOrNull() ?: -1
                    val p = price.toDoubleOrNull() ?: -1.0

                    if (q < 0) {
                        error = "La quantité doit être ≥ 0"
                        return@Button
                    }
                    if (p <= 0) {
                        error = "Le prix doit être > 0"
                        return@Button
                    }

                    val newProduct = ProductEntity(
                        id = product?.id ?: UUID.randomUUID().toString(),
                        name = name,
                        quantity = q,
                        price = p,
                        imageUrl = imageUrl // ← Ajouté
                    )
                    onSave(newProduct)
                }
            ) {
                Text(if (isEditMode) "Enregistrer" else "Ajouter")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Annuler") }
        }
    )
}