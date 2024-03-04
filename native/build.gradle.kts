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

@Suppress("UnstableApiUsage")
val compileRust = task("compileRust") {
    val libname = "geomag-jni"

    val scrDir = file("jni")
    val targetDir = scrDir.resolve("target")

    val output = providers.exec {
        commandLine(listOf("cargo", "build", "--release", "--verbose"))
        workingDir(scrDir)
    }.standardError.asBytes.get()

    targetDir.resolve("cargo.log").apply {
        writeBytes(output)
    }

    val libs = fileTree(targetDir) {
        include("*/*/libjni.so")
    }.files

    libs.forEach {
        val path = it.canonicalPath
        val target = when {
            path.contains("aarch64-linux-android") -> "arm64-v8a"
            path.contains("x86_64-linux-android") -> "x86_64"
            else -> ""
        }

        val outDir = File("src/main/libs", target)
        copy {
            from(it)
            rename("jni", libname)
            into(outDir)
        }
    }
}

tasks.whenTaskAdded {
    if (name == "javaPreCompileDebug" || name == "javaPreCompileRelease") {
        dependsOn(compileRust)
    }
}