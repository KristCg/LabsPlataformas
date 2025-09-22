package com.kriscg.laboratorio8.screens.locations

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kriscg.laboratorio8.data.LocationDb

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenLocationDetails(locationId: Int, onBack: () -> Unit) {
    val location = runCatching { LocationDb().getLocationById(locationId) }.getOrNull()

    if (location == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Location not found")
            Button(onClick = onBack) { Text("Back") }
        }
        return
    }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("ID: ${location.id}")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Name: ${location.name}")
            Text("Type: ${location.type}")
            Text("Dimension: ${location.dimension}")
        }
    }
}

