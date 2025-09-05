import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.lint
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.android.lint)
}

kotlin {
    jvm() {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilations.all {
            compilerOptions.configure {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }
    }
    // Target declarations - add or remove as needed below. These define
    // which platforms this KMP module supports.
    // See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
    androidLibrary {
        namespace = "app.prgghale.roomdb"
        compileSdk = 36
        minSdk = 21
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
//                    jvmTarget.set(JvmTarget.JVM_1_8)//FIXME no jvmTarget
                }
            }
        }
//        @OptIn(ExperimentalKotlinGradlePluginApi::class)
//        compilerOptions {
//            jvmTarget.set(JvmTarget.JVM_1_8)
//        }

        lint {
            warningsAsErrors = true
            abortOnError = true
            disable.addAll(
                listOf(
                    "MissingTranslation",
                    "ExtraTranslation",
                    "TypographyEllipsis",
                    "UnspecifiedImmutableFlag",
                    "UnusedResources",
                    "TypographyDashes"
                )
            )

            // disable("MissingTranslation",
            //         "ExtraTranslation",
            //         "TypographyEllipsis",
            //         "UnspecifiedImmutableFlag",
            //         "UnusedResources",
            //         "TypographyDashes")
        }


        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        packaging {
            resources {
                excludes += "META-INF/*"
                excludes += "META-INF/INDEX.LIST"
                excludes += "META-INF/*.kotlin_module"
                excludes += "META-INF/LICENSE"
                excludes += "META-INF/LICENSE.txt"
                excludes += "META-INF/NOTICE"
                excludes += "META-INF/io.netty.versions.properties"
                excludes += "META-INF/AL2.0"
                excludes += "META-INF/LGPL2.1"

            }
        }
    }

    // For iOS targets, this is also where you should
    // configure native binary output. For more information, see:
    // https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#build-xcframeworks

    // A step-by-step guide on how to include this library in an XCode
    // project can be found here:
    // https://developer.android.com/kotlin/multiplatform/migrate
    val xcfName = "commonKit"

    iosX64 {
        binaries.framework {
            baseName = xcfName
            linkerOpts.add("-lsqlite3")
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
            linkerOpts.add("-lsqlite3")
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
            linkerOpts.add("-lsqlite3")
        }
    }
//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach {
//        it.binaries.framework {
//            baseName = xcfName
//            isStatic = true
//    linkerOpts.add("-lsqlite3")
//        }
//    }

    // Source set declarations.
    // Declaring a target automatically creates a source set with the same name. By default, the
    // Kotlin Gradle Plugin creates additional source sets that depend on each other, since it is
    // common to share sources between related targets.
    // See: https://kotlinlang.org/docs/multiplatform-hierarchy.html
    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutines.core)
                // Add KMP dependencies here
                implementation(libs.androidx.room.runtime)
                implementation(libs.androidx.paging.common)
                implementation(libs.sqlite.bundled)

                implementation(libs.kotlinx.atomicfu)

//                implementation(libs.koin.core)
                //TODO ksp can not add here
                implementation("com.benasher44:uuid:0.8.2")
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.android)
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.test.runner)
                implementation(libs.androidx.test.core)
                implementation(libs.androidx.junit)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.swing)
            }
        }

        iosMain {
            dependencies {
                // Add iOS-specific dependencies here. This a source set created by Kotlin Gradle
                // Plugin (KGP) that each specific iOS target (e.g., iosX64) depends on as
                // part of KMP’s default source set hierarchy. Note that this source set depends
                // on common by default and will correctly pull the iOS artifacts of any
                // KMP dependencies declared in commonMain.
            }
        }
    }
}

dependencies {
//    ksp(libs.koin.ksp.compiler)
    ksp(libs.androidx.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
    generateKotlin = true
}

ksp {
    arg("KOIN_DEFAULT_MODULE", "true")
    arg("room.schemaLocation", "$projectDir/schemas")
}