package com.kriscg.laboratorio8.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kriscg.laboratorio8.data.CharacterDb
import com.kriscg.laboratorio8.models.Character

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenCharacters(
    onCharacterClick: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Characters") },
            )
        }
    ) { padding ->
        val characters = CharacterDb.getAllCharacters()
        LazyColumn(
            contentPadding = padding,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items(characters) { character ->
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Perfil",
                        modifier = Modifier.size(48.dp)

                    )
                    Column (modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "${character.name}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onCharacterClick(character.id) }
                                .padding(8.dp)
                        )
                        Text(
                            text = "${character.species} - ${character.status}",
                                    modifier = Modifier
                                    . fillMaxWidth ()
                                .clickable { onCharacterClick(character.id) }
                                .padding(8.dp)
                        )

                    }

                }

            }
        }
    }
}

