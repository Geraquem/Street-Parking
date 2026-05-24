package com.mmfsin.streetparking.presentation.map.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.streetparking.R
import com.mmfsin.streetparking.presentation.core.components.MediumText
import com.mmfsin.streetparking.presentation.core.theme.White

@Preview
@Composable
fun LocationErrorDialogPV() {
    LocationErrorDialog()
}

@Composable
fun LocationErrorDialog() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
            .background(White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MediumText(text = R.string.permission_error_description)
        Spacer(Modifier.height(16.dp))
        OutlinedButton(
            onClick = { openAppSettings(context) },
            modifier = Modifier.fillMaxWidth()
        ) {
            MediumText(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp),
                text = R.string.permission_error_button,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

private fun openAppSettings(context: Context) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    )
    context.startActivity(intent)
}
