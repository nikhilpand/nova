# NOVA Production ProGuard & R8 Optimization Rules

# Keep Jetpack Compose & Material 3
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Keep SQLCipher Native Encryption
-keep class net.sqlcipher.** { *; }
-keep class net.sqlcipher.database.** { *; }
-dontwarn net.sqlcipher.**

# Keep Room Entities & DAOs
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.**

# Keep Supabase & Ktor Serialization
-keepattributes Signature, InnerClasses, EnclosingMethod, *Annotation*
-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
}
-dontwarn io.ktor.**
-dontwarn io.github.jan_tennert.supabase.**

# Keep Hilt Dagger
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.internal.UnstableApi
-dontwarn dagger.hilt.**

# Keep ML Kit Text Recognition
-keep class com.google.mlkit.vision.** { *; }
-dontwarn com.google.mlkit.vision.**
