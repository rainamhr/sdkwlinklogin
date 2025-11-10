pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") } // for any JitPack dependencies
        flatDir {
            dirs("libs")
        }
    }
}

rootProject.name = "sdkwlinklogin"

// Include app and library module
include(":app")
include(":lib")
