plugins {
    application
    kotlin("jvm") version "1.7.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
}

group = "com.keygenqt"
version = "0.0.1"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {

    //////////////////////////////////
    //// Ktor
    val ktorVersion: String by project
    val kotlinVersion: String by project

    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

    //////////////////////////////////
    //// DI
    val koinVersion: String by project

    implementation("io.insert-koin:koin-ktor:$koinVersion")

    //////////////////////////////////
    //// DB
    val mysqlConnectorVersion: String by project
    val hikariCpVersion: String by project
    val flywayVersion: String by project
    val exposedVersion: String by project

    implementation("mysql:mysql-connector-java:$mysqlConnectorVersion")
    implementation("com.zaxxer:HikariCP:$hikariCpVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    implementation("org.flywaydb:flyway-core:$flywayVersion")
    implementation("org.flywaydb:flyway-mysql:$flywayVersion")

    //////////////////////////////////
    //// Other
    val logbackVersion: String by project

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
}