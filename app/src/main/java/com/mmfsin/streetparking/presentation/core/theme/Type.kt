package com.mmfsin.streetparking.presentation.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mmfsin.streetparking.R

val montserratRegular = FontFamily(
    Font(R.font.montserrat_regular, weight = FontWeight.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodySmall = TextStyle(
        fontFamily = montserratRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),

    bodyLarge = TextStyle(
        fontFamily = montserratRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),

    titleLarge = TextStyle(
        fontFamily = montserratRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
)