package com.chaima.smartshop.ui.theme //smartshopnav avantmodif

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chaima.smartshop.auth.LoginScreen
import com.chaima.smartshop.data.ProductEntity
import com.chaima.smartshop.ui.theme.ProductListScreen
import com.chaima.smartshop.ui.theme.ProductFormDialog
import com.chaima.smartshop.ui.theme.StatsScreen
import com.chaima.smartshop.ui.theme.ProductViewModel
@Composable
fun SmartShopNav() {
    var isLoggedIn by remember { mutableStateOf(false) }

    if (!isLoggedIn) {
        LoginScreen(onLoginSuccess = { isLoggedIn = true })
    } else {
        ProductNavHost()
    }
}

@Composable
fun ProductNavHost() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.ProductList) }
    var showFormDialog by remember { mutableStateOf(false) }
    var productToEdit by remember { mutableStateOf<ProductEntity?>(null) }

    val context = LocalContext.current
    val viewModel: ProductViewModel = viewModel { ProductViewModel(context) }

    val products by viewModel.products.collectAsState()
    val stats by viewModel.stats.collectAsState()

    when (currentScreen) {
        Screen.ProductList -> {
            ProductListScreen(
                products = products,
                onAddClick = {
                    productToEdit = null
                    showFormDialog = true
                },
                onEditClick = { product ->
                    productToEdit = product
                    showFormDialog = true
                },
                onDeleteClick = { viewModel.deleteProduct(it) },
                onStatsClick = { currentScreen = Screen.Stats }
            )

            if (showFormDialog) {
                // ✅ Appel simplifié (grâce à l'import)
                ProductFormDialog(
                    product = productToEdit,
                    onDismiss = { showFormDialog = false },
                    onSave = { product ->
                        if (productToEdit == null) {
                            viewModel.addProduct(product)
                        } else {
                            viewModel.updateProduct(product)
                        }
                        showFormDialog = false
                    }
                )
            }
        }

        Screen.Stats -> {
            StatsScreen(
                productCount = stats.count,
                totalValue = stats.totalValue,
                onBack = { currentScreen = Screen.ProductList }
            )
        }
    }
}

sealed class Screen {
    object ProductList : Screen()
    object Stats : Screen()
}