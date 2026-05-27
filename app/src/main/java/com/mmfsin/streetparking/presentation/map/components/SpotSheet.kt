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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmfsin.streetparking.R
import com.mmfsin.streetparking.domain.models.Spot
import com.mmfsin.streetparking.presentation.core.components.MediumText
import com.mmfsin.streetparking.presentation.core.components.SmallText
import com.mmfsin.streetparking.presentation.core.theme.BlueMedium
import com.mmfsin.streetparking.presentation.core.theme.GrayHard
import com.mmfsin.streetparking.presentation.core.theme.OrangeHard
import com.mmfsin.streetparking.presentation.core.theme.White
import com.mmfsin.streetparking.presentation.utils.formatDate
import com.mmfsin.streetparking.presentation.utils.getAddress

@Preview
@Composable
fun SpotSheetPV() {
    SpotSheet(
        spot = Spot(
            id = "",
            lat = 40.395228,
            lng = -3.710444,
            date = 1739728440000,
            reclaimed = 27
        ),
        {}, { _, _, _ -> }, { _, _ -> }
    )
}

@Composable
fun SpotSheet(
    spot: Spot,
    onDismiss: () -> Unit,
    reclaim: (String, Double, Double) -> Unit,
    howToGo: (Double, Double) -> Unit
) {

    val context = LocalContext.current
    var address by remember(spot.id) { mutableStateOf<String?>(null) }

    LaunchedEffect(spot.id) {
        context.getAddress(
            lat = spot.lat,
            lng = spot.lng,
            result = { adr -> address = adr }
        )
    }

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

        Text(
            text = if (address == "") stringResource(R.string.spot_dialog_not_address) else address ?: "",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )

        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            MediumText(R.string.spot_dialog_created)
            Spacer(Modifier.width(4.dp))
            Text(
                text = spot.date.formatDate(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            MediumText(R.string.spot_dialog_reclaimed)
            Spacer(Modifier.width(4.dp))
            Text(
                text = spot.reclaimed.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
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

        Buttons(
            reclaim = { reclaim(spot.id, spot.lat, spot.lng) },
            howToGo = { howToGo(spot.lat, spot.lng) }
        )

        Spacer(Modifier.height(24.dp))

        //        TextButton(onClick = { onDismiss() }, modifier = Modifier.fillMaxWidth()) {
        //            MediumText(R.string.spot_dialog_close, gravity = TextAlign.Center)
        //        }
    }
}

@Composable
fun Buttons(reclaim: () -> Unit, howToGo: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        SpotButton(
            modifier = Modifier.weight(0.5f).padding(end = 6.dp),
            text = R.string.spot_dialog_reclaim,
            icon = R.drawable.ic_map_spot,
            color = OrangeHard,
            onClick = { reclaim() }
        )

        SpotButton(
            modifier = Modifier.weight(0.5f).padding(start = 6.dp),
            text = R.string.spot_dialog_how_to_go,
            icon = R.drawable.ic_location_arrow,
            color = BlueMedium,
            onClick = { howToGo() }
        )
    }
}

@Composable
fun SpotButton(modifier: Modifier, text: Int, icon: Int, color: Color, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = color),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(icon), null)

            Spacer(Modifier.width(8.dp))

            MediumText(
                text = text,
                color = White,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}