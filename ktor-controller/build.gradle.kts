
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import com.bmuschko.gradle.docker.tasks.image.Dockerfile
import org.jetbrains.kotlin.util.suffixIfNot

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val kotlinLoggingJvmVersion: String by project
val serializationVersion: String by project

fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

plugins {
    id("application")
    id("com.bmuschko.docker-java-application")
    id("com.bmuschko.docker-remote-api")
    kotlin("plugin.serialization")
    kotlin("multiplatform")
}

repositories {
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    mavenCentral()
}

application {
    mainClass.set("ru.otus.otuskotlin.subscription.ApplicationKt")
}

kotlin {
    jvm {}
    sourceSets {
        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(ktor("core"))

                implementation(project(":common"))
                implementation(project(":api"))
                implementation(project(":mappers"))
                implementation(project(":mappers-log"))
                implementation(project(":stubs"))

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(ktor("core"))
                implementation(ktor("netty"))

                // jackson
                implementation(ktor("jackson", "serialization"))
                implementation(ktor("content-negotiation"))
                implementation(ktor("kotlinx-json", "serialization"))
                implementation(project(":lib-logging-logback"))

                implementation(ktor("locations"))
                implementation(ktor("caching-headers"))
                implementation(ktor("call-logging"))
                implementation(ktor("auto-head-response"))
                implementation(ktor("cors"))
                implementation(ktor("default-headers"))
                implementation(ktor("cors"))
                implementation(ktor("auto-head-response"))

                implementation(ktor("websockets"))
                implementation(ktor("auth"))
                implementation(ktor("auth-jwt"))

                implementation("ch.qos.logback:logback-classic:$logbackVersion")

                // transport models
                implementation(project(":common"))
                implementation(project(":api"))
                implementation(project(":mappers"))
                implementation(project(":lib-logging-common"))

                // Stubs
                implementation(project(":stubs"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(ktor("test-host"))
                implementation(ktor("content-negotiation", prefix = "client-"))
                implementation(ktor("websockets", prefix = "client-"))
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
                implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")
            }
        }
    }
    jvmToolchain(17)
}

tasks {
    val dockerJvmDockerfile by creating(Dockerfile::class) {
        group = "docker"
        from("openjdk:17")
        copyFile("app.jar", "app.jar")
        entryPoint("java", "-Xms256m", "-Xmx512m", "-jar", "/app.jar")
    }
    create("dockerBuildJvmImage", DockerBuildImage::class) {
        group = "docker"
        dependsOn(dockerJvmDockerfile, named("jvmJar"))
        doFirst {
            copy {
                from(named("jvmJar"))
                into("${project.buildDir}/docker/app.jar")
            }
        }
        images.add("${project.name}:${project.version}")
    }
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}