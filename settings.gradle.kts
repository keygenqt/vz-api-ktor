pluginManagement {

    val kotlinVersion: String by settings
    val ktorVersions: String by settings
    val dokkaVersions: String by settings
    val spotlessVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        id("io.ktor.plugin") version ktorVersions
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
        create("libsDatabase") {
            from(files("dependencies/database.toml"))
        }
        create("libsKtor") {
            from(files("dependencies/ktor.toml"))
        }
        create("libsOther") {
            from(files("dependencies/other.toml"))
        }
        create("libsTests") {
            from(files("dependencies/tests.toml"))
        }
    }
}

rootProject.name = "vz-api"

// core module
include(":features:core")

// apps modules
include(":features:kchat")
include(":features:ps")
