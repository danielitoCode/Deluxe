package com.elitec.deluxe.application.utils

import com.elitec.deluxe.domain.entities.Notification
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement

fun JsonElement.toNotification(): Notification = Json.decodeFromJsonElement(this)