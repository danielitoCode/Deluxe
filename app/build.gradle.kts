import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude
import java.io.FileInputStream
import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.devtools.ksp)
    //id("com.google.gms.google-services")
}

// Leer local.properties
val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(FileInputStream(localPropertiesFile))
    }
}

val versionMajor = 0
val versionMinor = 13
val versionPatch = 0

val appVersionName = "$versionMajor.$versionMinor.$versionPatch"
val appVersionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch

android {
    namespace = "com.elitec.deluxe"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.elitec.deluxe"
        minSdk = 26
        targetSdk = 36
        versionCode = appVersionCode
        versionName = appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "SUPABASE_PROYECT_URL", "\"${localProperties.getProperty("SUPABASE_PROYECT_URL")}\"")
            buildConfigField("String", "APPWRITE_PUBLIC_ENDPOINT", "\"${localProperties.getProperty("APPWRITE_PUBLIC_ENDPOINT")}\"")
            buildConfigField("String", "APPWRITE_DATABASE_ID", "\"${localProperties.getProperty("APPWRITE_DATABASE_ID")}\"")
            buildConfigField("String", "APPWRITE_BUCKECT_ID", "\"${localProperties.getProperty("APPWRITE_BUCKECT_ID")}\"")
            buildConfigField("String", "APPWRITE_PROJECT_ID", "\"${localProperties.getProperty("APPWRITE_PROJECT_ID")}\"")
            buildConfigField("String", "SUPABASE_PROYECT_KEY", "\"${localProperties.getProperty("SUPABASE_PROYECT_KEY")}\"")
            buildConfigField("String", "WHATSAPP_LINK_GROUP", "\"${localProperties.getProperty("WHATSAPP_LINK_GROUP")}\"")
            buildConfigField("String", "REALTIME_PROMO_CHANNEL_ID", "\"${localProperties.getProperty("REALTIME_PROMO_CHANNEL_ID")}\"")
            buildConfigField("String", "REALTIME_NOTIFICATION_CHANNEL_ID", "\"${localProperties.getProperty("REALTIME_NOTIFICATION_CHANNEL_ID")}\"")
            buildConfigField("String", "REALTIME_PROMO_CONFIRMATIONS_ID", "\"${localProperties.getProperty("REALTIME_PROMO_CONFIRMATIONS_ID")}\"")
            buildConfigField("String", "REALTIME_UPDATES_CHANNEL_ID", "\"${localProperties.getProperty("REALTIME_UPDATES_CHANNEL_ID")}\"")
        }
        release {
            buildConfigField("String", "APPWRITE_PUBLIC_ENDPOINT", "\"${localProperties.getProperty("APPWRITE_PUBLIC_ENDPOINT")}\"")
            buildConfigField("String", "APPWRITE_DATABASE_ID", "\"${localProperties.getProperty("APPWRITE_DATABASE_ID")}\"")
            buildConfigField("String", "APPWRITE_BUCKECT_ID", "\"${localProperties.getProperty("APPWRITE_BUCKECT_ID")}\"")
            buildConfigField("String", "APPWRITE_PROJECT_ID", "\"${localProperties.getProperty("APPWRITE_PROJECT_ID")}\"")
            buildConfigField("String", "SUPABASE_PROYECT_URL", "\"${localProperties.getProperty("SUPABASE_PROYECT_URL")}\"")
            buildConfigField("String", "SUPABASE_PROYECT_KEY", "\"${localProperties.getProperty("SUPABASE_PROYECT_KEY")}\"")
            buildConfigField("String", "WHATSAPP_LINK_GROUP", "\"${localProperties.getProperty("WHATSAPP_LINK_GROUP")}\"")
            buildConfigField("String", "REALTIME_PROMO_CHANNEL_ID", "\"${localProperties.getProperty("REALTIME_PROMO_CHANNEL_ID")}\"")
            buildConfigField("String", "REALTIME_NOTIFICATION_CHANNEL_ID", "\"${localProperties.getProperty("REALTIME_NOTIFICATION_CHANNEL_ID")}\"")
            buildConfigField("String", "REALTIME_PROMO_CONFIRMATIONS_ID", "\"${localProperties.getProperty("REALTIME_PROMO_CONFIRMATIONS_ID")}\"")
            buildConfigField("String", "REALTIME_UPDATES_CHANNEL_ID", "\"${localProperties.getProperty("REALTIME_UPDATES_CHANNEL_ID")}\"")


            isMinifyEnabled = true
            isShrinkResources = true
            // isDebuggable = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // signingConfig = signingConfigs.getByName("debug")
            //signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true // Asegura que BuildConfig se genere
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.process)
    //implementation(libs.androidx.compose.animation.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //Firebase Dependencies
    /*implementation(platform("com.google.firebase:firebase-bom:34.5.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging")*/

    // HttpClient
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.cio)
    //implementation(libs.ktor.serialization.kotlinx.xml)
    implementation(libs.ktor.serialization.kotlinx.json) { //Esto es para evitar conflictos con el R8
        exclude(group = "com.fasterxml.jackson.core")
        exclude(group = "com.fasterxml.jackson.dataformat", module = "jackson-dataformat-xml")
    }
    implementation(libs.ktor.client.logs)
    // implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.websocket)
    implementation(libs.ktor.client.test)

    // Navegacion
    implementation(libs.androidx.navigation.compose)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Lotties
    implementation(libs.lotties.compose)

    // Icons
    implementation(libs.composeIcons.fontAwesome)
    implementation(libs.androidx.icons.extended)

    // Di
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.compose.navigation)
    implementation(libs.koin.test)

    // Supabase
    /*implementation(platform("io.github.jan-tennert.supabase:bom:3.2.5"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:auth-kt")
    implementation("io.github.jan-tennert.supabase:realtime-kt")*/

    // Google Identity
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    // Kotlin DateTime
    implementation(libs.kotlinx.datetime)

    // AppWrite
    implementation(libs.sdk.for1.android)

    // Coik
    implementation(libs.coil.compose)

    // Permission
    //implementation(libs.compose.permission)
    implementation(libs.compose.permission)

    //implementation("com.github.parse-community.Parse-SDK-Android:parse:1.18.5")

    // --- OkHttp / Okio for AppWrite + Ktor ---
    implementation(libs.okhttp)
    implementation(libs.okio)

    // DataStore Preferences
    implementation(libs.androidx.datastore.preferences)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)

    // Sqlite Cipher
    implementation(libs.room.cipher)

    // Google Crypto
    implementation(libs.google.crypto)

    // PubNub
    // implementation(libs.pubnub.android)
    implementation(libs.pusher.java.client)

    // Shimmer
    implementation(libs.shimer.compose)

    // Logs
    //implementation(libs.microsoft.analytics)
    //implementation(libs.microsoft.crashes)

    //ConnectionStatus
    implementation(libs.konnection.status)

    // Resend
    implementation(libs.resend.java)
    implementation("com.mailersend:java-sdk:1.4.1")

    implementation("com.sun.mail:android-mail:1.6.0")
    implementation("com.sun.mail:android-activation:1.6.0")

    implementation("io.mailtrap:mailtrap-java:1.1.0")
}
