import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.serialization)
    id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
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
            isStatic = false
            export("dev.icerock.moko:resources:0.24.3")
            export("dev.icerock.moko:graphics:0.9.0")
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            //Sqldelight
            implementation(libs.sqldelight.coroutines.extensions)

            //Moko
            api(libs.moko.resources)

            //Network
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.koin.core)
            api(libs.moko.resources)

            //Datetime
            implementation(libs.datetime)

            //Logs
            api(libs.napier)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

multiplatformResources {
    resourcesPackage.set("com.example.kursovikkmp")
}

android {
    namespace = "com.example.kursovikkmp"
    compileSdk = 34
    defaultConfig {
        minSdk = 28
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
