package com.kriscg.laboratorio8.screens.locations

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kriscg.laboratorio8.ViewModel.LocationDetailViewModel
import com.kriscg.laboratorio8.data.LocationDb
import com.kriscg.laboratorio8.models.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenLocationDetails(
    locationId: Int,
    onBack: () -> Unit
) {
    val viewModel: LocationDetailViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LocationDetailViewModel(locationId) as T
            }
        }
    )

    val state = viewModel.state.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Location details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Text("Cargando detalle...")
                    }
                }
                state.isError -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error al cargar ubicación")
                        Button(onClick = { viewModel.retry() }) {
                            Text("Reintentar")
                        }
                    }
                }
                state.data != null -> {
                    val location = state.data!!
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp)
                    ) {
                        Text("ID: ${location.id}")
                        Text("Name: ${location.name}")
                        Text("Type: ${location.type}")
                        Text("Dimension: ${location.dimension}")
                    }
                }
            }
        }
    }
}
