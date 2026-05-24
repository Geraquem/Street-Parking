package com.mmfsin.streetparking.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mmfsin.streetparking.R
import com.mmfsin.streetparking.presentation.core.components.MediumText
import com.mmfsin.streetparking.presentation.core.theme.White

@Preview
@Composable
fun SpotItemPV() {
    SpotItem()
}

@Composable
fun SpotItem() {
    Box(modifier = Modifier.fillMaxWidth().background(White)) {
        Column() {
            MediumText(text = R.string.app_name)
        }
    }
}
