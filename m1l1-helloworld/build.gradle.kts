plugins {
    kotlin("jvm")
}

val kotlinVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}")
    testImplementation(kotlin("test-junit"))
}