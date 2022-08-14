package com.shiinasoftware.mooovie.ui.shared.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat


@SuppressLint("SimpleDateFormat")
fun convertDateStringToFullDate(date: String): String {
    if (date.isEmpty()) return ""
    val parser = SimpleDateFormat("yyyy-MM-dd")
    val formatter = SimpleDateFormat("MMM yyyy")
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    return formatter.format(parser.parse(date))
}