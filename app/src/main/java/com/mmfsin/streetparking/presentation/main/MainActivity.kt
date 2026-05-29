package com.mmfsin.streetparking.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.mmfsin.streetparking.presentation.core.navigation.NavigationWrapper
import com.mmfsin.streetparking.presentation.core.theme.StreetParkingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StreetParkingTheme { NavigationWrapper(viewModel) }
        }
    }
}