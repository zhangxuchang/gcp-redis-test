plugins {
    kotlin("jvm") version "2.0.20"
    application
}

group = "redis.test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// define the main class
application {
    mainClass.set("MainKt")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("redis.clients:jedis:7.0.0")
    implementation("io.valkey:valkey-java:5.5.0")
    implementation("ch.qos.logback:logback-classic:1.5.18")
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}