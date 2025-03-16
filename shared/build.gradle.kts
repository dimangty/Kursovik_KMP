import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.serialization)
    alias(libs.plugins.sqldelight)
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
            //implementation(libs.kotlin.coroutines)
            //Sqldelight
            implementation(libs.sqldelight.coroutines.extensions)

            //Moko
            api(libs.moko.resources)

            //Network
            implementation(libs.ktor.client.core)
            //implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.koin.core)
            api(libs.moko.resources)

            //
            implementation(libs.androidx.lifecycle.viewmodel)

            //Datetime
            implementation(libs.datetime)

            //Logs
            api(libs.napier)

            implementation(libs.touchLab)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(libs.ktor.client.android)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.sqldelight.android.driver)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.ios)
            implementation(libs.sqldelight.native.driver)
        }
    }
}

multiplatformResources {
    resourcesPackage.set("com.example.kursovikkmp")
}

android {
    namespace = "com.example.kursovikkmp"
    compileSdk = 35
    defaultConfig {
        minSdk = 28
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.example.kursovikkmp")
            generateAsync.set(true)
        }
    }
    linkSqlite = true
}