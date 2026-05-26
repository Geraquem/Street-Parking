@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.streetparking.presentation.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmfsin.streetparking.R
import com.mmfsin.streetparking.domain.models.Spot
import com.mmfsin.streetparking.presentation.core.components.MediumText
import com.mmfsin.streetparking.presentation.core.components.SmallText
import com.mmfsin.streetparking.presentation.core.theme.BlueMedium
import com.mmfsin.streetparking.presentation.core.theme.GrayHard
import com.mmfsin.streetparking.presentation.core.theme.White
import com.mmfsin.streetparking.presentation.utils.formatDate
import com.mmfsin.streetparking.presentation.utils.getAddress

@Preview
@Composable
fun SpotSheetPV() {
    SpotSheet(
        spot = Spot(
            id = "",
            lat = 40.397043,
            lng = -3.715447,
            date = 1739728440000,
            reclaimed = 27
        ),
        {}, {}
    )
}

@Composable
fun SpotSheet(
    spot: Spot,
    onDismiss: () -> Unit,
    howToGo: () -> Unit
) {

    var address by remember { mutableStateOf<String?>(null) }

    LocalContext.current.getAddress(
        lat = spot.lat,
        lng = spot.lng,
        result = { adr -> address = adr }
    )

    Column(
        modifier = Modifier.fillMaxWidth()
            .background(White)
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(64.dp)
                    .height(5.dp)
                    .background(
                        color = GrayHard,
                        shape = RoundedCornerShape(50)
                    )
            )
        }

        Spacer(Modifier.height(24.dp))

        address?.let { adr ->
            Text(
                text = adr,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(spot.date.formatDate())

        Spacer(Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            SmallText(R.string.spot_dialog_reclaimed)
            Spacer(Modifier.width(4.dp))
            Text(
                text = spot.reclaimed.toString(), style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }


        Spacer(Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(R.drawable.ic_info), null,
                tint = BlueMedium
            )
            Spacer(Modifier.width(8.dp))
            SmallText(text = R.string.spot_dialog_remember, color = BlueMedium)
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = BlueMedium),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(R.drawable.ic_location_arrow), null)

                Spacer(Modifier.width(8.dp))

                MediumText(
                    text = R.string.spot_dialog_how_to_go,
                    color = White,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }

        Spacer(Modifier.height(6.dp))

        TextButton(onClick = { onDismiss() }, modifier = Modifier.fillMaxWidth()) {
            MediumText(R.string.spot_dialog_close, gravity = TextAlign.Center)
        }
    }
}