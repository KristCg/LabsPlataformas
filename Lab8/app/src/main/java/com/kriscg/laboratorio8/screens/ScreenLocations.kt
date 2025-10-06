package com.kriscg.laboratorio8.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kriscg.laboratorio8.ViewModel.LocationsViewModel
import com.kriscg.laboratorio8.data.LocationDb
import com.kriscg.laboratorio8.models.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenLocations(
    onLocationClick: (Int) -> Unit,
    viewModel: LocationsViewModel = viewModel()
) {
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
                        Text("Cargando ubicaciones...")
                    }
                }
                state.isError -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error al cargar ubicaciones"
                        )
                        Button(
                            onClick = { viewModel.retry() }
                        ) {
                            Text(
                                text = "Reintentar"
                            )
                        }
                    }
                }
                state.data != null -> {
                    val locations: List<Location> = state.data!!
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(locations) { location ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onLocationClick(location.id) }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = location.name
                                )
                                Text(
                                    text = location.type
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}