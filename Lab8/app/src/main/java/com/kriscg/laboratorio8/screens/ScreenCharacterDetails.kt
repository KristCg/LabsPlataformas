package com.kriscg.laboratorio8.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.kriscg.laboratorio8.R
import com.kriscg.laboratorio8.ViewModel.CharacterDetailViewModel
import com.kriscg.laboratorio8.models.Character

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenCharacterDetails(
    characterId: Int,
    onBack: () -> Unit
) {

    val viewModel: CharacterDetailViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CharacterDetailViewModel(characterId) as T
            }
        }
    )

    val state = viewModel.state.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Character Details"
                    ) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Cargando"
                        )
                    }
                }
                state.isError -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error al cargar personaje"
                        )
                        Spacer(modifier = Modifier.height(8.dp))
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
                    val character: Character = state.data!!
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        AsyncImage(
                            model = character.image,
                            contentDescription = character.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clip(CircleShape),
                            placeholder = painterResource(R.drawable.carga),
                            error = painterResource(R.drawable.error)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Name: ${character.name}"
                        )
                        Text(
                            text = "Species: ${character.species}"
                        )
                        Text(
                            text = "Status: ${character.status}"
                        )
                        Text(
                            text = "Gender: ${character.gender}"
                        )
                    }
                }
            }
        }
    }
}
