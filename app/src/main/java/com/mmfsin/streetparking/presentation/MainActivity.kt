package com.mmfsin.streetparking.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mmfsin.streetparking.presentation.core.navigation.NavigationWrapper
import com.mmfsin.streetparking.presentation.core.theme.StreetParkingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { StreetParkingTheme { NavigationWrapper() } }
    }
}