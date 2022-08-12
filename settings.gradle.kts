pluginManagement {

    val kotlinVersion: String by settings
    val dokkaVersions: String by settings
    val spotlessVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        id("org.jetbrains.dokka") version dokkaVersions
        id("com.diffplug.spotless") version spotlessVersion
        id("org.jetbrains.kotlin.plugin.serialization") version kotlinVersion
    }
}

dependencyResolutionManagement {

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    }

    versionCatalogs {
        create("libs") {
            from(fileTree("dependencies"))
        }
    }
}

rootProject.name = "vz-api"

// core module
include(":features:core")

// apps modules
include(":features:kchat")
include(":features:ps")