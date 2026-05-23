package com.mmfsin.streetparking.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.streetparking.presentation.core.theme.BlueMedium
import com.mmfsin.streetparking.presentation.core.theme.White

//@Preview
@Composable
fun LoadingFullScreen() {
    Box(Modifier.fillMaxSize().background(White), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            Modifier.size(64.dp),
            strokeWidth = 6.dp,
            color = BlueMedium,
            strokeCap = StrokeCap.Round
        )
    }
}

@Preview
@Composable
fun MiniLoading() {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            Modifier.size(40.dp),
            strokeWidth = 6.dp,
            color = BlueMedium,
            strokeCap = StrokeCap.Round
        )
    }
}