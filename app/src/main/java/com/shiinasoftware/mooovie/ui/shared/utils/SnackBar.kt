package com.shiinasoftware.mooovie.ui.shared.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showShortSnackBar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        .show()
}

fun showLongSnackBar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        .show()
}

