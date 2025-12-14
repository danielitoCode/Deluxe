package com.elitec.deluxe.application.utils

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.elitec.deluxe.data.realtime.PusherManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppLifecycleObserver(
    private val pusherManager: PusherManager
) : DefaultLifecycleObserver {

    // Called when app enters foreground
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        // Connect if session exists (do not init here with userId)
        // Optionally you may re-open subscriptions. Prefer to call init with userId from SessionManager after login
    }

    // Called when app goes to background
    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        // Disconnect to reduce traffic and presence events
        CoroutineScope(Dispatchers.IO).launch {
            // choose between full disconnect or unsubscribeAll
            pusherManager.disconnect()
            // optionally disconnect completely:
            // pubNubManager.disconnect()
        }
    }
}
