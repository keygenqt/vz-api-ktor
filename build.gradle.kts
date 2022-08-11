plugins {
    application
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.dokka")
    id("com.diffplug.spotless")
}

group = "com.keygenqt"
version = "0.0.1"

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

dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.bundles.other)
    implementation(libs.bundles.database)
    testImplementation(libs.bundles.tests)
}