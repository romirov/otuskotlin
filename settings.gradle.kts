rootProject.name = "otuskotlin"

pluginManagement {
    val kotlinVersion: String by settings
    val kotestVersion: String by settings
    val openapiVersion: String by settings
    val ktorVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion apply false
        id("io.kotest.multiplatform") version kotestVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
        id("io.ktor.plugin") version ktorVersion
    }
}

//include("m1l1-helloworld")
include("api")
include("common")
include("mappers")
include("ktor-controller")
