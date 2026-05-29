package com.mmfsin.streetparking.presentation.whereparked

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mmfsin.streetparking.presentation.core.theme.RedHard

@Preview
@Composable
fun WhereParkedPV() {
    WhereParkedContent()
}

@Composable
fun WhereParkedScreen() {
    WhereParkedContent()
}

@Composable
fun WhereParkedContent() {
    Box(
        modifier = Modifier.fillMaxSize().background(RedHard)
    )
}