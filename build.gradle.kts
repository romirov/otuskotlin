import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val jvmVersion: String by project

plugins {
    id("org.jetbrains.kotlin.jvm")
    application
}

group = "org.otus.otuskotlin"
version = "1.0-SNAPSHOT"

subprojects {
    group = rootProject.group
    version = rootProject.version
    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "$jvmVersion"
    }
}