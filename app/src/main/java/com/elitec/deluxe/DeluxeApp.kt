package com.elitec.deluxe

import android.app.Application
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ProcessLifecycleOwner
import com.elitec.deluxe.application.di.KoinModules
import com.elitec.deluxe.application.utils.AppLifecycleObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.koinInject
import org.koin.core.context.startKoin
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.getKoin
import java.lang.Exception
import kotlin.time.Clock


class DeluxeApp: Application() {

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@DeluxeApp)
            modules(
                // KoinModules.supabaseModules(),
                KoinModules.appWriteModules(this@DeluxeApp),
                //KoinModules.securityModules(),
                KoinModules.realTimeModules(),
                KoinModules.roomModules(this@DeluxeApp),
                KoinModules.repositoriesModules(),
                KoinModules.domainModules(),
                KoinModules.viewModelsModule(),
                KoinModules.emailModules()
            )
        }

        // Register lifecycle observer to react to foreground/background
        val observer: AppLifecycleObserver = getKoin().get()
        ProcessLifecycleOwner.get().lifecycle.addObserver(observer)

        setupExceptionHandler()
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    private fun setupExceptionHandler() {
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->

            // FirebaseCrashlytics.getInstance().recordException(throwable)

            // Mostrar Toast en el hilo principal
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(
                    this@DeluxeApp,
                    "Se ha reiniciado la aplicación por un error fatal",
                    Toast.LENGTH_SHORT
                ).show()
            }

            // Manejar la excepción y reiniciar
            // handleUncaughtException(throwable)

            // Llamar al manejador por defecto si existe
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }
}
