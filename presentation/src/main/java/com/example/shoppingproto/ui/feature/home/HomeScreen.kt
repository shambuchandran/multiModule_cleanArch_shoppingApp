package com.example.shoppingproto.ui.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.domain.model.Product
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = koinViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
    Scaffold {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            when (uiState.value) {
                is HomeScreenUiEvents.Loading -> {
                    CircularProgressIndicator()
                }
                is HomeScreenUiEvents.Success -> {
                    val data = (uiState.value as HomeScreenUiEvents.Success)
                    HomeContent(data.featured,data.popularProducts)
                }
                is HomeScreenUiEvents.Error -> {
                    Text((uiState.value as HomeScreenUiEvents.Error).message)

                }
            }
        }
    }
}

@Composable
fun HomeContent(featured: List<Product>, popularProducts: List<Product>) {
    LazyColumn {
        item {
            if(featured.isNotEmpty()){
                HomeProductRow(featured, title = "Featured")
                Spacer(modifier = Modifier.size(16.dp))
            }
            if (popularProducts.isNotEmpty()){
                HomeProductRow(popularProducts, title = "Popular products")
            }
        }
    }
    
}

@Composable
fun HomeProductRow(products: List<Product>, title: String) {
    Column {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                title, style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(
                    Alignment.CenterStart
                )
            )
            Text(
                "view all",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(
                    Alignment.CenterEnd
                )
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow {
            items(products) { product ->
                ProductItem(product)
            }
        }
    }


}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .size(width = 126.dp, height = 144.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = .3f))
    ) {
        Column(Modifier.fillMaxSize()) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
            )
            Spacer(Modifier.size(8.dp))
            Text(
                product.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                "$$product.price",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}
