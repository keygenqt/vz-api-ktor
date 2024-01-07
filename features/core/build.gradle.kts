plugins {
    `java-library`
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

group = "com.keygenqt.core"
version = "0.0.1"

dependencies {
    implementation(libsKtor.bundles.ktor)
    implementation(libsOther.bundles.other)
    implementation(libsDatabase.bundles.database)
    testImplementation(libsTests.bundles.tests)
}
