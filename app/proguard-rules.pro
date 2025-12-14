# ===============================
# ATRIBUTOS CRÍTICOS PARA KOTLIN + KTOR + APPWRITE + SERIALIZATION
# ===============================
-keepattributes RuntimeVisibleAnnotations,
                RuntimeInvisibleAnnotations,
                Signature,
                InnerClasses,
                EnclosingMethod,
                LocalVariableTable,
                LocalVariableTypeTable

# Mantener metadatos de Kotlin
-keep class kotlin.Metadata { *; }

# ===============================
# APPWRITE
# ===============================
-keep class io.appwrite.** { *; }
-dontwarn io.appwrite.**

# ===============================
# KTOR CLIENT
# ===============================
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**
-keep class io.ktor.client.engine.android.** { *; }

# ===============================
# KOTLINX SERIALIZATION
# ===============================
-keep class kotlinx.serialization.** { *; }
-keepclassmembers class ** {
    @kotlinx.serialization.Serializable *;
}
-keepclassmembers class * {
    kotlinx.serialization.KSerializer serializer(...);
}
-keepclassmembers class **$Companion { *; }
-keepclassmembers class * implements kotlinx.serialization.KSerializer { *; }

# Mantener implementaciones internas
-keep class kotlinx.serialization.internal.** { *; }
-keepattributes *Annotation*, Signature, InnerClasses

# ===============================
# COROUTINES
# ===============================
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

# ===============================
# TUS MODELOS
# ===============================
-keep @kotlinx.serialization.Serializable class com.elitec.deluxe.domain.entities.** { *; }

# ===============================
# OKHTTP / OKIO
# ===============================
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**
-keep class okio.** { *; }
-dontwarn okio.**

# ===============================
# GSON
# ===============================
-keep class com.google.gson.** { *; }

# ---- CRÍTICO: Mantener anotaciones de parámetros ----
-keepattributes RuntimeVisibleParameterAnnotations

# ---- CRÍTICO: Mantener constructores para clases serializables ----
-keepclassmembers class com.elitec.deluxe.** {
    <init>(...);
}

# ---- CRÍTICO: Mantener los nombres de los campos de tus modelos ----
-keepclassmembers class com.elitec.deluxe.domain.entities.** {
    <fields>;
}

# ---- TEMPORAL: Mantener todos los miembros para detectar problema ----
-keepclassmembers class ** {
    *;
}
