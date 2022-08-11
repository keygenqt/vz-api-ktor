plugins {
    application
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.dokka")
}

group = "com.keygenqt"
version = "0.0.1"

dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.bundles.other)
    implementation(libs.bundles.database)
    testImplementation(libs.bundles.tests)
}