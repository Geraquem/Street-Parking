package com.mmfsin.streetparking.presentation.utils

import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController

fun SoftwareKeyboardController.closeKeyboard(focusManager: FocusManager) {
    this.hide()
    focusManager.clearFocus()
}