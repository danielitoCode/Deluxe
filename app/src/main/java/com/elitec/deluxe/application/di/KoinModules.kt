package com.elitec.deluxe.application.di

import android.content.Context
import androidx.room.Room
import com.elitec.deluxe.BuildConfig
import com.elitec.deluxe.application.bd.DeluxeBD
import com.elitec.deluxe.application.utils.AppLifecycleObserver
import com.elitec.deluxe.data.bd.dao.JoyasDao
import com.elitec.deluxe.data.cache.LocalWhatsapMembershipCacheImpl
import com.elitec.deluxe.data.net.appwriteRepos.AccountNetRepoImpl
import com.elitec.deluxe.data.net.appwriteRepos.CategoriasNetRepoImpl
import com.elitec.deluxe.data.net.appwriteRepos.FileUploadRepoImpl
import com.elitec.deluxe.data.net.appwriteRepos.JoyaNetRepoImpl
import com.elitec.deluxe.data.net.appwriteRepos.SessionRepositoryImpl
import com.elitec.deluxe.data.net.appwriteRepos.SessionUtilsRepoImpl
import com.elitec.deluxe.data.net.credentials.DeviceRemoteRepo
import com.elitec.deluxe.data.repos.data.CategoriasRepositoryImpl
import com.elitec.deluxe.data.repos.data.JoyaRepositoryImpl
import com.elitec.deluxe.data.repos.security.DeviceLocalRepo
import com.elitec.deluxe.data.security.SqlCipherKeyManager
import com.elitec.deluxe.domain.core.accounts.AccountManager
import com.elitec.deluxe.domain.core.accounts.SessionManager
import com.elitec.deluxe.domain.core.datas.CategoriasManager
import com.elitec.deluxe.data.bd.repos.CategoryLocalRepository
import com.elitec.deluxe.data.bd.repos.JoyasLocalRepositoryImpl
import com.elitec.deluxe.data.cache.OtpDataStore
import com.elitec.deluxe.data.cache.UserPrefsDataStore
import com.elitec.deluxe.data.cache.VerificationLocalDataStore
import com.elitec.deluxe.data.realtime.PusherManager
import com.elitec.deluxe.domain.core.accounts.EmailSenderManager
import com.elitec.deluxe.domain.core.accounts.GMailSenderImplement
import com.elitec.deluxe.domain.core.accounts.SenderMailManagerImpl
import com.elitec.deluxe.domain.core.accounts.ResendEmailSenderManagerImpl
import com.elitec.deluxe.domain.core.accounts.VerificationManager
import com.elitec.deluxe.domain.core.command.ActionDispatcher
import com.elitec.deluxe.domain.core.command.CommandProcessor
import com.elitec.deluxe.domain.core.command.ComposeDeepLinkNavigator
import com.elitec.deluxe.domain.core.datas.CategoryManager
import com.elitec.deluxe.domain.core.datas.InfoManager
import com.elitec.deluxe.domain.core.datas.JoyaManager
import com.elitec.deluxe.domain.core.datas.JoyasManager
import com.elitec.deluxe.domain.core.datas.TestManager
import com.elitec.deluxe.domain.core.file.UploadCompressedImageManager
import com.elitec.deluxe.domain.core.notification.local.AndroidPushManager
import com.elitec.deluxe.domain.core.notification.local.LocalNotificationsManager
import com.elitec.deluxe.domain.core.notification.local.PushManager
import com.elitec.deluxe.domain.core.notification.realtime.RemoteNotificationManager
import com.elitec.deluxe.domain.core.security.DeviceRegistrationManager
import com.elitec.deluxe.domain.core.whatsap.CheckWhatsAppGroupMembership
import com.elitec.deluxe.domain.core.whatsap.ShareCartToWhatsAppUseCase
import com.elitec.deluxe.domain.dataRepositories.cache.LocalWhatsapMembershipCache
import com.elitec.deluxe.domain.dataRepositories.net.AccountRepository
import com.elitec.deluxe.domain.dataRepositories.net.DataRepo
import com.elitec.deluxe.domain.dataRepositories.net.FileRepository
import com.elitec.deluxe.domain.dataRepositories.net.SessionRepository
import com.elitec.deluxe.domain.dataRepositories.net.SessionUtils
import com.elitec.deluxe.domain.entities.Categoria
import com.elitec.deluxe.domain.entities.Joya
import com.elitec.deluxe.presentation.navigation.DeepLinkNavigator
import com.elitec.deluxe.presentation.navigation.DeepLinkRouter
import com.elitec.deluxe.presentation.navigation.NavigationEventBus
import com.elitec.deluxe.presentation.viewmodels.AccountViewModel
import com.elitec.deluxe.presentation.viewmodels.ConnectedViewModel
import com.elitec.deluxe.presentation.viewmodels.DataViewModel
import com.elitec.deluxe.presentation.viewmodels.NotificationViewModel
import com.elitec.deluxe.presentation.viewmodels.OtpViewModel
import com.elitec.deluxe.presentation.viewmodels.RemoteAlertsViewModel
import com.elitec.deluxe.presentation.viewmodels.SaleViewModel
import com.elitec.deluxe.presentation.viewmodels.SessionsViewModel
import com.elitec.deluxe.presentation.viewmodels.TestViewModel
import com.elitec.deluxe.presentation.viewmodels.UploadImageViewModel
import com.elitec.deluxe.presentation.viewmodels.VerificationViewModel
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AesGcmKeyManager
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.resend.Resend
import dev.tmapps.konnection.Konnection
import io.appwrite.Client
import io.appwrite.services.Account
import io.appwrite.services.Databases
import io.appwrite.services.Storage
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object KoinModules {
    //private lateinit var roomDB: DeluxeBD
    //private lateinit var androidKeysetManager: KeysetHandle

    private val app_id = "2084167"
    private val key = "e2fdd069cb2fdf606a97"
    private val secret = "69e327cd72848afc0c49"
    private val cluster = "mt1"

    private fun getApiKey(): String {
        return if (BuildConfig.DEBUG) {
            "100cf65a1b92ea5968ce0c5a0e2c17d11a33dca443a7792ad98e7e2b375ad577a76dc8fdf5b5137d37f1aca959b8c3d63b7eda79802813a407038824e581d198e915a1178996aa48b861eed36537f22c1269a9b95750c3a01f726d11972aab64f2eb7084fea065fe4ee8e1a3dfaccbfdca3c17c3367702e6b532b2ab56de68ff" // Key para desarrollo
        } else {
            "standard_1eeef52e40779f8005823e5907e479cb48eb1962e1ac06d692815e701005aa2aa7c9fa4bee468bdcb9d7e7daaf095ce4d1af87c39f08d5b15e38339d7a684d6ad0b60418facf48e5e7da0edc0753bfd955ec56104f89116417c2f3c02c1a764ab52c83820ac2784cf16cd37a3fb73f00513658aa0cb1861f6324d48b78579341" // Key para release
        }
    }

    fun appWriteModules(context: Context): Module = module {
        single {
            Client(
                context = context
            )
                .setEndpoint("https://fra.cloud.appwrite.io/v1")
                .setProject("68fd82470024761abb8c")
                .setDevKey(getApiKey())
                .setSelfSigned(false)
        }
        single { Account(get()) }
        single { Databases(get()) }
        single { Storage(get()) }
    }

    fun emailModules(): Module = module {
        single { Resend("re_WuPhLgAa_Abjf7siauH5LWfVVCYPrjhmF") }
        single { VerificationLocalDataStore(get()) }
        single { VerificationManager(get(), get()) }
        // single { ResendEmailSenderManagerImpl(get(), get(), get()) }
        single<EmailSenderManager> { GMailSenderImplement(get(), get()) }
    }

    fun securityModules(): Module = module {
        single {
                AndroidKeysetManager.Builder()
                .withSharedPref(
                    get(),
                    "master_keyset",
                    "my_pref")
                .withKeyTemplate(AesGcmKeyManager.aes256GcmTemplate())
                .withMasterKeyUri("android-keystore://master_key")
                .build()
                .keysetHandle
        }
        single { get<KeysetHandle>().getPrimitive(Aead::class.java) }
        single { SqlCipherKeyManager(get()) }
    }

    fun realTimeModules(): Module = module {
        single { CommandProcessor() }
        single { RemoteNotificationManager(get(), get(), get(), get()) }
        single { AppLifecycleObserver(get()) }
        single { Pusher(key, PusherOptions().setCluster(cluster)) }
        single { PusherManager(get()) }
        single { DeepLinkRouter() }
        single { NavigationEventBus() }
        single<DeepLinkNavigator> { ComposeDeepLinkNavigator(get(), get()) }
        single<PushManager> { AndroidPushManager(get()) }
        single { ActionDispatcher(get(),get(), get()) }
    }

    fun roomModules(context: Context): Module = module {
        single {
            Room.databaseBuilder(
                context,
                DeluxeBD::class.java,
                "DeluxeBD"
            )
                .fallbackToDestructiveMigration(true)
                //.openHelperFactory(get())
                .build()
        }
        //single<CategoriasDao> { get<DeluxeBD>().categoriaDao() }
        single<JoyasDao> { get<DeluxeBD>().joyaDao() }
        //single { get<DeluxeBD>().getTestDao() }
    }

    fun repositoriesModules(): Module = module {
        single {
            HttpClient(CIO) {
                install(ContentNegotiation) { json() }
                // timeouts, logging, etc.
            }
        }
        factory<AccountRepository> { AccountNetRepoImpl(get()) }
        factory<SessionRepository> { SessionRepositoryImpl(get()) }
        single<DataRepo<Joya>> { JoyaNetRepoImpl(get()) }
        factory { JoyaNetRepoImpl(get()) }
        single<DataRepo<Categoria>> { CategoriasNetRepoImpl(get()) }
        factory<SessionUtils> { SessionUtilsRepoImpl(get()) }
        factory<FileRepository> { FileUploadRepoImpl(get()) }
        single<LocalWhatsapMembershipCache> { LocalWhatsapMembershipCacheImpl(get()) }
        single { JoyaRepositoryImpl(get(), get()) }
        single { CategoriasRepositoryImpl(get(), get()) }
        factory { DeviceLocalRepo(get()) }
        factory { DeviceRemoteRepo(get()) }
        single { CategoryLocalRepository(get()) }
        single { JoyasLocalRepositoryImpl(get()) }
        single { OtpDataStore(get()) }
        single { UserPrefsDataStore() }
    }

    fun domainModules(): Module = module {
        single { LocalNotificationsManager() }
        single { AccountManager( get()) }
        single { SessionManager(get(), get(), get(), get()) }
        single { UploadCompressedImageManager(get()) }
        single { CheckWhatsAppGroupMembership(get()) }
        single { ShareCartToWhatsAppUseCase(get()) }
        single { CategoriasManager(get(), get()) }
        single { JoyasManager(get(), get()) }
        //single { DataManager(get(), get(), get()) }
        single { TestManager(get()) }
        single { DeviceRegistrationManager(get(), get()) }
        single { CategoryManager(get(), get()) }
        single { JoyaManager(get(), get()) }
        single<InfoManager<Categoria>> { CategoriasManager(get(), get()) }
        single<InfoManager<Joya>> { JoyasManager(get(), get()) }
        single { Konnection.createInstance(enableDebugLog = true) }
    }

    fun viewModelsModule(): Module = module {
        viewModel { AccountViewModel(get(), get()) }
        viewModel { SessionsViewModel(get(), get(), get(), get()) }
        viewModel { NotificationViewModel(get()) }
        viewModel { UploadImageViewModel(get(), get()) }
        viewModel { SaleViewModel(get(), get(), get()) }
        viewModel { DataViewModel(get(), get(), get()) }
        viewModel { TestViewModel(get()) }
        viewModel { ConnectedViewModel(get()) }
        viewModel { RemoteAlertsViewModel(get(), get()) }
        viewModel { VerificationViewModel(get(), get(), get()) }
        viewModel { OtpViewModel(get()) }
    }
}
