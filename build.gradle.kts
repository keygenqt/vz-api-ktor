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