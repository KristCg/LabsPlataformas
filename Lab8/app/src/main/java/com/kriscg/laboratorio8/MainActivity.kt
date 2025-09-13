package com.kriscg.laboratorio8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kriscg.laboratorio8.ui.theme.Laboratorio8Theme
import com.kriscg.laboratorio8.navigation.SetupNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio8Theme {
                SetupNavGraph()
            }
        }
    }
}
