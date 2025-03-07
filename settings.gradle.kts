enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "KursovikKMP"
include(":androidApp")
include(":shared")
include(":androidApp:core")
include(":androidApp:feature_news")
include(":androidApp:feature_favorites")
