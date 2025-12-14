package com.elitec.deluxe.application.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.elitec.deluxe.presentation.navigation.DeepLinkRouter
import com.elitec.deluxe.presentation.navigation.NavigationEventBus
import org.koin.core.context.GlobalContext

class NotificationClickReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val deepLink = intent.getStringExtra("deepLink")
        if (deepLink.isNullOrBlank()) return

        // Obtén los singletons de Koin
        val navigationEvents: NavigationEventBus = GlobalContext.get().get()
        val deepLinkRouter: DeepLinkRouter = GlobalContext.get().get()

        // Resuelve el deepLink a un destino tipado
        val destination = deepLinkRouter.resolve(deepLink) ?: run {
            Log.w("NotificationReceiver", "DeepLink no reconocido: $deepLink")
            return
        }

        // Envía el destino al flujo de navegación
        navigationEvents.navigate(destination)
        Log.d("NotificationReceiver", "Navegando desde notificación a: $destination")
    }
}
