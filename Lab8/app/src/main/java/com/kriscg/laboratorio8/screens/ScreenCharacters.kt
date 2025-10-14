package com.kriscg.laboratorio8.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.kriscg.laboratorio8.ViewModel.CharactersViewModel
import androidx.compose.foundation.lazy.items
import com.kriscg.laboratorio8.ViewModel.CharactersViewModelFactory
import com.kriscg.laboratorio8.data.room.AppDatabase


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenCharacters(
database: AppDatabase,
onCharacterClick: (Int) -> Unit
) {
    val factory = CharactersViewModelFactory(database)
    val viewModel: CharactersViewModel = viewModel(factory = factory)
    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Characters") })
        }
    ) { padding ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            state.isError -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error al cargar personajes")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.retry() }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
            state.data != null -> {
                LazyColumn(
                    contentPadding = padding,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    items(state.data) { character ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onCharacterClick(character.id) }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = character.image,
                                contentDescription = character.name,
                                modifier = Modifier
                                    .size(64.dp)
                                    .padding(end = 8.dp)
                            )
                            Column {
                                Text(text = character.name)
                                Text(text = "${character.species} - ${character.status}")
                            }
                        }
                    }
                }
            }
        }
    }
}
