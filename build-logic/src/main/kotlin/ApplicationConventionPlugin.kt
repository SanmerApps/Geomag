import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class ApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                compileSdk = 34
                buildToolsVersion = "34.0.0"
                ndkVersion = "26.3.11579264"

                defaultConfig {
                    minSdk = 29
                    targetSdk = compileSdk
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_21
                    targetCompatibility = JavaVersion.VERSION_21
                }
            }

            extensions.configure<JavaPluginExtension> {
                toolchain {
                    languageVersion.set(JavaLanguageVersion.of(21))
                }
            }

            extensions.configure<KotlinAndroidProjectExtension> {
                jvmToolchain(21)

                sourceSets.all {
                    languageSettings {
                        optIn("androidx.compose.material3.ExperimentalMaterial3Api")
                        optIn("androidx.compose.foundation.ExperimentalFoundationApi")
                        optIn("androidx.compose.foundation.layout.ExperimentalLayoutApi")
                        optIn("kotlin.ExperimentalStdlibApi")
                    }
                }
            }
        }
    }
}
