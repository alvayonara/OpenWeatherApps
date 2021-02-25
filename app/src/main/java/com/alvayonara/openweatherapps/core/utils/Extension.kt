package com.alvayonara.openweatherapps.core.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.snack(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}