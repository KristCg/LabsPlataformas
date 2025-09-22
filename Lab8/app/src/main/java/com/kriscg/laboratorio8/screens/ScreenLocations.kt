package com.kriscg.laboratorio8.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kriscg.laboratorio8.data.LocationDb

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenLocations(
    onLocationClick: (Int) -> Unit
) {
    val locations = LocationDb().getAllLocations()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Locations") }) }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
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
                    Text(text = location.name)
                    Text(text = location.type)
                }
            }
        }
    }
}