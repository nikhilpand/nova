plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.hilt)
  alias(libs.plugins.ksp)
  alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.nova"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.example.nova"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "0.1.0-alpha"
    }

    signingConfigs {
        create("release") {
            val keystorePath = System.getenv("KEYSTORE_FILE") ?: (project.rootDir.path + "/release.keystore")
            val keystoreFile = file(keystorePath)
            if (keystoreFile.exists()) {
                storeFile = keystoreFile
                storePassword = System.getenv("KEYSTORE_PASSWORD") ?: "NovaReleaseStorePassword2026"
                keyAlias = System.getenv("KEY_ALIAS") ?: "nova_release_key"
                keyPassword = System.getenv("KEY_PASSWORD") ?: "NovaReleaseKeyPassword2026"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            val releaseConfig = signingConfigs.findByName("release")
            if (releaseConfig != null && releaseConfig.storeFile != null) {
                signingConfig = releaseConfig
            } else {
                signingConfig = signingConfigs.getByName("debug")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
      compose = true
    }

    packaging {
      resources {
        excludes += "/META-INF/{AL2.0,LGPL2.1}"
      }
    }
}

dependencies {
  val composeBom = platform(libs.androidx.compose.bom)
  implementation(composeBom)
  androidTestImplementation(composeBom)

  // Project Submodules
  implementation(project(":core"))
  implementation(project(":domain"))
  implementation(project(":data"))
  implementation(project(":network"))
  implementation(project(":database"))
  implementation(project(":auth"))
  implementation(project(":messaging"))
  implementation(project(":media"))
  implementation(project(":calls"))
  implementation(project(":ai"))
  implementation(project(":notifications"))
  implementation(project(":settings"))
  implementation(project(":designsystem"))
  implementation(project(":communities"))
  implementation(project(":security"))

  // Core Android
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)

  // Architecture Components
  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.androidx.lifecycle.viewmodel.compose)

  // Compose UI
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.material3)
  debugImplementation(libs.androidx.compose.ui.tooling)
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  debugImplementation(libs.androidx.compose.ui.test.manifest)

  // Navigation
  implementation(libs.androidx.navigation.compose)

  // Hilt DI
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)
  implementation(libs.hilt.navigation.compose)

  // Ktor Client
  implementation(libs.ktor.client.cio)

  // Firebase
  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.messaging)

  // Credential Manager (Passkeys)
  implementation(libs.credentials)
  implementation(libs.credentials.play.services)

  // Room + SQLCipher
  implementation(libs.room.runtime)
  implementation(libs.room.ktx)
  ksp(libs.room.compiler)
  implementation(libs.sqlcipher.android)

  // Media
  implementation(libs.coil.compose)
  implementation(libs.media3.exoplayer)
  implementation(libs.camerax.camera2)
  implementation(libs.camerax.lifecycle)
  implementation(libs.camerax.view)

  // ML Kit
  implementation(libs.mlkit.text.recognition)

  // Serialization & Coroutines
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.coroutines.android)

  // UI Helpers
  implementation(libs.haze)
  implementation(libs.accompanist.permissions)
  implementation(libs.zxing.core)

  // Local tests
  testImplementation(libs.junit)
  testImplementation(libs.kotlinx.coroutines.test)

  // Instrumented tests
  androidTestImplementation(libs.androidx.test.core)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.androidx.test.runner)
  androidTestImplementation(libs.androidx.test.espresso.core)
}
