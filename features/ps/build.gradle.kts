plugins {
    `java-library`
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

group = "com.keygenqt.ps"
version = "0.0.1"

dependencies {
    implementation(project(":features:core"))
}

dependencies {
    implementation(libsKtor.bundles.ktor)
    implementation(libsOther.bundles.other)
    implementation(libsDatabase.bundles.database)
    testImplementation(libsTests.bundles.tests)
}
