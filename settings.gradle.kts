rootProject.name = "otuskotlin"

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        id("org.jetbrains.kotlin.jvm") version "$kotlinVersion"
    }
}

include("m1l1-helloworld")