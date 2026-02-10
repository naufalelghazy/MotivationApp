package com.motivasi.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.motivasi.app.ui.MotivasiNavigation
import com.motivasi.app.ui.theme.MotivasiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MotivasiTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MotivasiNavigation()
                }
            }
        }
    }
}
