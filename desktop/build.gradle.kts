import org.gradle.kotlin.dsl.implementation
import org.gradle.kotlin.dsl.project
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
//    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.compose.hot.reload)
}

group = "app.prgghale.roomdb.app.desktop"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

//java {
//    toolchain {
//        languageVersion.set(JavaLanguageVersion.of(17))
//    }
//}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
    sourceSets {
        val main by getting {
            dependencies {
                implementation(project(":common"))
                implementation(project(":shared"))
//                implementation(compose.desktop.currentOs)// multiplatform is ok jvm must api at library
                implementation(libs.kotlinx.coroutines.swing)
                implementation(libs.koin.core)
            }
        }
    }
}
dependencies {

}

//https://medium.com/@makeevrserg/compose-desktop-shadowjar-1cba3aba9a58
compose.desktop {
    application {
        mainClass = "app.prgghale.roomdb.app.desktop.MainKt"
        jvmArgs += listOf("--add-opens", "java.base/java.lang=ALL-UNNAMED")
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "app.prgghale.roomdb.app.desktop"
//            description = ""
            packageVersion = "1.0.0"
            println("JMODS Folder: ${compose.desktop.application.javaHome}/jmods/java.base.jmod")
            includeAllModules = false
        }
    }
}
