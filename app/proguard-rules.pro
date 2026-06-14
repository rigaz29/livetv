# --- Kotlinx Serialization ---
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.**
# Keep @Serializable classes' synthetic serializer companions.
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}
-if @kotlinx.serialization.Serializable class ** {
    static **$* *;
}
-keepclassmembers class <2>$<3> {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,includedescriptorclasses class com.bagas.livetv.**$$serializer { *; }
-keepclassmembers class com.bagas.livetv.** {
    *** Companion;
}
-keepclasseswithmembers class com.bagas.livetv.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# --- Retrofit / OkHttp ---
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.**
-keepattributes Signature, Exceptions

# --- Media3 ---
-dontwarn androidx.media3.**

# --- Models (parsed via reflection-free serialization, but keep names for safety) ---
-keep class com.bagas.livetv.data.remote.dto.** { *; }
