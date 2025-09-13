package com.kriscg.laboratorio8.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kriscg.laboratorio8.data.CharacterDb

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenCharacterDetails(
    characterId: Int,
    onBack: () -> Unit
) {
    val character = runCatching { CharacterDb.getCharacterById(characterId) }.getOrNull()

    if (character == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Character not found")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack) {
                Text("Back")
            }
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Character Details") },
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
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Name: ${character.name}")
            Text("Species: ${character.species}")
            Text("Status: ${character.status}")
            Text("Gender: ${character.gender}")
        }
    }
}


