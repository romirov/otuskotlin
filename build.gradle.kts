import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val jvmVersion: String by project

plugins {
    kotlin("multiplatform") apply false
    kotlin("jvm") apply false
}

group = "org.otus.otuskotlin"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "$jvmVersion"
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile> {
        kotlinOptions.jvmTarget = "$jvmVersion"
    }
}