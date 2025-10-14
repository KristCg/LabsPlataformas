package com.kriscg.laboratorio8.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kriscg.laboratorio8.ViewModel.LocationsViewModel
import com.kriscg.laboratorio8.ViewModel.LocationsViewModelFactory
import com.kriscg.laboratorio8.data.room.AppDatabase
import com.kriscg.laboratorio8.models.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenLocations(
    database: AppDatabase,
    onLocationClick: (Int) -> Unit
) {
    val factory = LocationsViewModelFactory(database)
    val viewModel: LocationsViewModel = viewModel(factory = factory)
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = { TopAppBar(title = { Text("Locations") }) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Cargando ubicaciones...")
                    }
                }
                state.isError -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error al cargar ubicaciones")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.retry() }) {
                            Text("Reintentar")
                        }
                    }
                }
                state.data != null -> {
                    LazyColumn(
                        contentPadding = padding,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        items(state.data!!) { location ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onLocationClick(location.id) }
                                    .padding(8.dp)
                            ) {
                                Text(text = location.name)
                                Text(text = location.type)
                            }
                        }
                    }
                }
            }
        }
    }
}


