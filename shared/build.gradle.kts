plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)

    kotlin("plugin.serialization") version "1.9.20"
    id("app.cash.sqldelight") version "2.0.0"
}

repositories {
    google()
    mavenCentral()
}

sqldelight {
    databases {
        create("CoinDataDatabase") {
            packageName.set("com.example")
        }
    }
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
        }
    }

    val coroutinesVersion = "1.7.3"
    val ktorVersion = "2.3.5"
    val sqlDelightVersion = "2.0.0"
    val koin = "3.2.0"
    val dateTimeVersion = "0.4.1"

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.store5)
                implementation("org.jetbrains.kotlinx:atomicfu:0.18.5")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("app.cash.sqldelight:runtime:2.0.0")
                implementation("app.cash.sqldelight:coroutines-extensions:2.0.0")
                implementation("io.insert-koin:koin-core:$koin")
                //implementation("org.jetbrains.kotlinx:kotlinx-datetime:$dateTimeVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("app.cash.sqldelight:android-driver:$sqlDelightVersion")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                implementation("app.cash.sqldelight:native-driver:$sqlDelightVersion")
            }
        }
    }
}

android {
    namespace = "com.example.kmptest"
    compileSdk = 34
    defaultConfig {
        minSdk = 29
    }
}
dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
}
