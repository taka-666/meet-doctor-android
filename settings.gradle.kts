pluginManagement {
    repositories {
        google() // Repositori untuk plugin Google
        mavenCentral() // Repositori utama Maven
        gradlePluginPortal() // Repositori plugin Gradle
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google() // Repositori utama Android
        mavenCentral() // Repositori Maven untuk pustaka Java/Kotlin

    }
}
rootProject.name = "Meet-Doctor"
include(":app")
