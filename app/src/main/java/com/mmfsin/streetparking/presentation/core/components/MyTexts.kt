package com.mmfsin.streetparking.presentation.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.mmfsin.streetparking.R
import com.mmfsin.streetparking.presentation.core.theme.Black

@Preview(showBackground = true)
@Composable
fun TextsPV() {
    Column {
        SmallText(text = R.string.app_name)
        MediumText(text = R.string.app_name)
        BigText(text = R.string.app_name)
    }
}

@Composable
fun SmallText(
    modifier: Modifier = Modifier,
    text: Int,
    color: Color = Black,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        modifier = Modifier,
        text = stringResource(text),
        style = MaterialTheme.typography.bodySmall,
        color = color,
        fontWeight = fontWeight
    )
}

@Composable
fun MediumText(
    modifier: Modifier = Modifier,
    text: Int,
    color: Color = Black,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        modifier = Modifier,
        text = stringResource(text),
        style = MaterialTheme.typography.bodyLarge,
        color = color,
        fontWeight = fontWeight
    )
}

@Composable
fun BigText(
    modifier: Modifier = Modifier,
    text: Int,
    color: Color = Black,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        modifier = Modifier,
        text = stringResource(text),
        style = MaterialTheme.typography.titleLarge,
        color = color,
        fontWeight = fontWeight
    )
}

