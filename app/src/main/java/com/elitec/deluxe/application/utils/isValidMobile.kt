package com.elitec.deluxe.application.utils

fun isValidMobile(mobile: String): Boolean {
    val mobileRegex = Regex("^\\+?[0-9]{7,15}$")
    return mobileRegex.matches(mobile.trim())
}