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
        maven {
            setUrl("https://jitpack.io")
            content { includeGroup ("com.github.taka-666") }// Untuk pustaka yang di-host di JitPack
    }
}
}
rootProject.name = "Meet-Doctor"
include(":app")
