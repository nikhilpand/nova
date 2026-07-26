pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
            content {
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
            }
        }
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "NOVA"

// Core Android modules
include(":app")
include(":core")
include(":domain")
include(":data")
include(":network")
include(":database")

// Feature modules
include(":auth")
include(":messaging")
include(":media")
include(":calls")
include(":ai")
include(":notifications")
include(":settings")
include(":designsystem")
include(":communities")
include(":security")

// Extension modules
include(":plugins")
include(":sdk")
include(":extensions")

// Test & Benchmark
include(":benchmark")
