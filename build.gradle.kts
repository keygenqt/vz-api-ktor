plugins {
    application
    kotlin("jvm")
    id("io.ktor.plugin")
    id("org.jetbrains.dokka")
    id("com.diffplug.spotless")
    id("org.jetbrains.kotlin.plugin.serialization")
}

group = "com.keygenqt.app"
version = "1.0.0"

application {
    mainClass.set("com.keygenqt.app.ApplicationKt")
}

ktor {
    fatJar {
        archiveFileName.set("VZ-API.jar")
    }
}

// configure dokka for multi module
tasks.withType<org.jetbrains.dokka.gradle.DokkaMultiModuleTask>().configureEach {
    outputDirectory.set(rootDir.resolve("api"))
    failOnWarning.set(true)
}

subprojects {

    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "com.diffplug.spotless")

    // configure spotless for modules
    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude("**/build/**", "**/V*__*.kt")
            ktlint(libs.versions.ktlintVersion.get())
                .editorConfigOverride(
                    mapOf(
                        "disabled_rules" to "no-wildcard-imports",
                    )
                )
            licenseHeaderFile("$rootDir/copyright.txt")
        }
    }

    // configure dokka for modules
    tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
        suppressInheritedMembers.set(true)
        dokkaSourceSets {
            configureEach {
                includes.from("dokka.md")
                includeNonPublic.set(true)
            }
        }
    }
}

// tasks
apply(from = "tasks/docs.gradle.kts")

// modules
dependencies {
    implementation(project(":features:core"))
    implementation(project(":features:kchat"))
    implementation(project(":features:ps"))
}

// libraries
dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.bundles.other)
    implementation(libs.bundles.database)
    testImplementation(libs.bundles.tests)
}