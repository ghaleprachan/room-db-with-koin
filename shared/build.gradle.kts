import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
}

group = "app.prgghale.roomdb"
version = "1.0-SNAPSHOT"

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
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
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                }
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
                optIn("androidx.compose.material3.ExperimentalMaterial3Api")
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        commonMain.dependencies {
            implementation(libs.kotlin.stdlib)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)

            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.animation)
            implementation(compose.animationGraphics)
            implementation(compose.material)
            implementation(compose.materialIconsExtended)
            implementation(compose.material3)
            implementation(compose.uiUtil)
            implementation(compose.components.resources)

            // Compose Navigation
            implementation(libs.jetbrains.androidx.navigation.compose)
            implementation(libs.jetbrains.androidx.lifecycle.viewmodel)
            implementation(libs.jetbrains.androidx.lifecycle.viewmodel.compose)
            implementation(libs.jetbrains.androidx.lifecycle.runtime.compose)


            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)

            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)

            implementation(project(":common"))

            implementation(libs.coil)
            implementation(libs.coil.compose)

            implementation(libs.kotlinx.atomicfu)

        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        val jvmMain by getting {
            dependencies {
                api(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutines.swing)
            }
        }

        val jvmTest by getting

        androidMain {
            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.kotlinx.coroutines.android)
                implementation(libs.androidx.lifecycle.viewmodel.ktx)
                implementation(libs.androidx.lifecycle.runtime.ktx)
                implementation(libs.koin.android)
                implementation(compose.preview)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }
}

android {
    namespace = "app.prgghale.roomdb"
    compileSdk = 36
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
    buildFeatures {
        buildConfig = false
        compose = true
    }
    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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

dependencies {

}

compose.resources {
    publicResClass = true
    packageOfResClass = "app.prgghale.roomdb"
    generateResClass = always
}