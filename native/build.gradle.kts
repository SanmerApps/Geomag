plugins {
    alias(libs.plugins.pro.library)
}

android {
    namespace = "dev.sanmer.geomag"

    sourceSets {
        getByName("main") {
            jniLibs.srcDir("src/main/libs")
        }
    }
}

dependencies {
    implementation(libs.kotlinx.datetime)
}