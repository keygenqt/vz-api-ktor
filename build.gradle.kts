plugins {
    application
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.dokka")
    id("com.diffplug.spotless")
}

group = "com.keygenqt.app"
version = "0.0.1"

tasks.withType<org.jetbrains.dokka.gradle.DokkaMultiModuleTask>().configureEach {
    outputDirectory.set(rootDir.resolve("api"))
    failOnWarning.set(true)
}

subprojects {

    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "com.diffplug.spotless")

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

    tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {

        // Suppress all inherited members that were not overridden in a given class.
        suppressInheritedMembers.set(true)

        dokkaSourceSets {
            configureEach {
                // List of files with module and package documentation
                // https://kotlinlang.org/docs/reference/kotlin-doc.html#module-and-package-documentation
                includes.from("dokka.md")
                // Use to include or exclude non-public members
                includeNonPublic.set(true)
            }
        }
    }
}

tasks.register("generateDocs") {
    doLast {
        exec {
            commandLine = listOf("./gradlew", "spotlessApply")
        }
    }
    doLast {
        exec {
            commandLine = listOf("./gradlew", "dokkaHtmlMultiModule")
        }
    }
    doLast {
        exec {
            commandLine = listOf("mkdocs", "build")
        }
    }
    doLast {
        exec {
            commandLine = listOf("mv", "api", "site")
        }
    }
}

// modules
dependencies {
    // core
    implementation(project(":features:core"))
    // apps
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