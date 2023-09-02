import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import java.time.Instant

plugins {
    id("geomag.android.application")
    id("geomag.android.application.compose")
    id("geomag.android.hilt")
    id("geomag.android.room")
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.protobuf)
}

val baseVersionName = "0.5.1"
val isDevVersion: Boolean get() = exec("git tag -l v${baseVersionName}").isEmpty()
val verNameSuffix: String get() = if (isDevVersion) ".dev" else ""

android {
    namespace = "com.sanmer.geomag"

    defaultConfig {
        applicationId = namespace
        versionName = "${baseVersionName}${verNameSuffix}.${commitId}"
        versionCode = commitCount

        resourceConfigurations += arrayOf("en", "zh-rCN")
        multiDexEnabled = true

        ndk {
            abiFilters += arrayOf("arm64-v8a", "armeabi-v7a", "x86", "x86_64")
        }
    }

    val releaseSigning = if (project.hasReleaseKeyStore) {
        signingConfigs.create("release") {
            storeFile = project.releaseKeyStore
            storePassword = project.releaseKeyStorePassword
            keyAlias = project.releaseKeyAlias
            keyPassword = project.releaseKeyPassword
            enableV2Signing = true
            enableV3Signing = true
        }
    } else {
        signingConfigs.getByName("debug")
    }

    buildTypes {
        debug {
            versionNameSuffix = ".debug"
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        all {
            signingConfig = releaseSigning
            buildConfigField("Boolean", "IS_DEV_VERSION", isDevVersion.toString())
            buildConfigField("String", "BUILD_TIME", "\"${Instant.now()}\"")
        }
    }

    buildFeatures {
        aidl = true
        buildConfig = true
    }

    packaging.resources.excludes += setOf(
        "META-INF/**",
        "okhttp3/**",
        "kotlin/**",
        "org/**",
        "**.properties",
        "**.bin",
        "**/*.proto",
        "**/*.java"
    )

    applicationVariants.configureEach {
        outputs.configureEach {
            this as ApkVariantOutputImpl
            outputFileName = "Geomag-${versionName}-${versionCode}-${name}.apk"
        }
    }

    splits {
        abi {
            isEnable = true
            isUniversalApk = true
        }
    }
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(fileTree("src/main/golang/libs") {
        include("*.jar", "*.aar")
    })

    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.documentfile)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.service)
    implementation(libs.androidx.lifecycle.viewModel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.protobuf.kotlin.lite)

    implementation(libs.square.moshi)
    ksp(libs.square.moshi.kotlin)

    implementation(libs.timber)
}