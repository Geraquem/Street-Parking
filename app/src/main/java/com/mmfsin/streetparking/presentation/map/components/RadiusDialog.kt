package com.mmfsin.streetparking.presentation.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mmfsin.streetparking.R
import com.mmfsin.streetparking.domain.models.RadiusTypes.Companion.getAllRadius
import com.mmfsin.streetparking.domain.models.RadiusTypes.Companion.getTypeByRadius
import com.mmfsin.streetparking.presentation.core.components.MediumText
import com.mmfsin.streetparking.presentation.core.theme.White

@Preview
@Composable
fun RadiusDialogPV() {
    RadiusDialog(500.0, {}, {})
}

@Composable
fun RadiusDialog(
    actualRadius: Double,
    onDismiss: () -> Unit,
    newRadius: (Double) -> Unit
) {
    val actualRadius = getTypeByRadius(actualRadius)
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(White)
                .padding(top = 16.dp, bottom = 8.dp)
        ) {
            MediumText(
                modifier = Modifier.padding(start = 16.dp),
                text = R.string.radius_dialog_title,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(12.dp))
            LazyColumn {
                items(getAllRadius()) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clickable(onClick = { newRadius(item.radius) })
                            .padding(vertical = 4.dp)
                            .background(White),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = item == actualRadius,
                            onClick = { newRadius(item.radius) },
                            modifier = Modifier.padding(start = 8.dp)
                        )
                        MediumText(text = item.text)
                    }
                }
            }
        }
    }
}
