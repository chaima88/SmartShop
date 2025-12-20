package com.chaima.smartshop.ui.theme //statescreenavantmodif

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api // ✅ Import ajouté

@OptIn(ExperimentalMaterial3Api::class) // ✅ Annotation ajoutée
@Composable
fun StatsScreen(
    productCount: Int,
    totalValue: Double,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Statistiques") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icons.AutoMirrored.Filled.ArrowBack
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Nombre total de produits : $productCount", style = MaterialTheme.typography.headlineSmall)
            Text("Valeur totale du stock : ${"%.2f".format(totalValue)} DT", style = MaterialTheme.typography.headlineSmall)
        }
    }
}