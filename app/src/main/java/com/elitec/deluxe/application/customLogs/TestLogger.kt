package com.elitec.deluxe.application.customLogs

object TestLogger {

    fun info(msg: String, data: Any? = null, tag: String? = null) {
        println("✅ [INFO]${tag?.let { " [$it]" } ?: ""} $msg ${data?.let { " -> $it" } ?: ""}")
    }

    fun warn(msg: String, data: Any? = null, tag: String? = null) {
        println("⚠️ [WARN]${tag?.let { " [$it]" } ?: ""} $msg ${data?.let { " -> $it" } ?: ""}")
    }

    fun error(msg: String, throwable: Throwable? = null, tag: String? = null) {
        println("❌ [ERROR]${tag?.let { " [$it]" } ?: ""} $msg ${throwable?.message ?: ""}")
        throwable?.printStackTrace()
    }
}
