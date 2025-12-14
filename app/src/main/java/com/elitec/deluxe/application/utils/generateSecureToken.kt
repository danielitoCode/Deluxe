package com.elitec.deluxe.application.utils

import java.security.SecureRandom

fun generateSecureToken(length: Int = 12): String {
    val allowed = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val random = SecureRandom()

    return buildString {
        repeat(length) {
            append(allowed[random.nextInt(allowed.length)])
        }
    }
}
