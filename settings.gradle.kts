pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

// Allow to use "implementation(project.common)" instead of "implementation(project(":common"))
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "GoogleAuthenticationDemo"
include(":app")
include(":google_auth")
