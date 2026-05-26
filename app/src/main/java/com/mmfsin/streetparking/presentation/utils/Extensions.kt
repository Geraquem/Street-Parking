package com.mmfsin.streetparking.presentation.utils

import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun SoftwareKeyboardController.closeKeyboard(focusManager: FocusManager) {
    this.hide()
    focusManager.clearFocus()
}

fun Long.formatDate(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm dd MMM yyyy")
        .withZone(ZoneId.systemDefault())

    return formatter.format(Instant.ofEpochMilli(this))
        .replaceFirstChar { it.uppercase() }
}