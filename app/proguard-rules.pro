-verbose
-dontpreverify
-optimizationpasses 5
-dontskipnonpubliclibraryclasses

-dontwarn org.conscrypt.**
-dontwarn kotlinx.serialization.**

# Keep DataStore fields
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite* {
   <fields>;
}

# Keep Native library
-keep class dev.sanmer.geomag.MagneticField { *; }
-keepclassmembers class dev.sanmer.geomag.Geomag {
    native <methods>;
}

-repackageclasses com.sanmer.geomag