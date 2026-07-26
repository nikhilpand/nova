plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.serialization)
    application
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("com.nova.backend.ApplicationKt")
}

dependencies {
    implementation(libs.ktor.client.core)
    implementation(libs.kotlinx.serialization.json)
}
