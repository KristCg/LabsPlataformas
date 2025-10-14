package com.kriscg.laboratorio8.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import androidx.compose.ui.unit.dp
import com.kriscg.laboratorio8.R
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import com.kriscg.laboratorio8.ViewModel.UserViewModel
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ScreenLogin(
    modifier: Modifier = Modifier,
    onStartClicked: () -> Unit,
    userViewModel: UserViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(120.dp))
        AsyncImage(
            model = R.drawable.logo,
            contentDescription = "Logo",
            modifier = Modifier
                .width(300.dp)
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Ingresa tu nombre") },
            modifier = Modifier.width(300.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                scope.launch {
                    if (name.isNotBlank()) {
                        userViewModel.saveUserName(name)
                        onStartClicked()
                    }
                }
            },
            modifier = Modifier.width(300.dp)
        ) {
            Text(text = "Empezar")
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Kristel Castillo - #241294",
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

