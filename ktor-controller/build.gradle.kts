//
//import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
//import com.bmuschko.gradle.docker.tasks.image.Dockerfile
//import org.jetbrains.kotlin.util.suffixIfNot
//
//val ktorVersion: String by project
//val kotlinVersion: String by project
//val logbackVersion: String by project
//val kotlinLoggingJvmVersion: String by project
//val serializationVersion: String by project
//
//fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
//    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"
//
//plugins {
//    id("application")
//    id("com.bmuschko.docker-java-application")
//    id("com.bmuschko.docker-remote-api")
//    kotlin("plugin.serialization")
//    kotlin("multiplatform")
//}
//
//repositories {
//    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
//    mavenCentral()
//}
//
//application {
//    mainClass.set("ru.otus.otuskotlin.subscription.app.ApplicationKt")
//    //mainClass.set("io.ktor.server.cio.EngineMain")
//}
//
//kotlin {
//    jvm {}
//    sourceSets {
//        @Suppress("UNUSED_VARIABLE")
//        val commonMain by getting {
//            dependencies {
//                implementation(kotlin("stdlib-common"))
//                implementation(ktor("core"))
//
//                implementation(project(":common"))
//                implementation(project(":api"))
//                implementation(project(":mappers"))
//                implementation(project(":mappers-log"))
//                implementation(project(":stubs"))
//
//                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
//                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
//            }
//        }
//        @Suppress("UNUSED_VARIABLE")
//        val commonTest by getting {
//            dependencies {
//                implementation(kotlin("test-common"))
//                implementation(kotlin("test-annotations-common"))
//            }
//        }
//        @Suppress("UNUSED_VARIABLE")
//        val jvmMain by getting {
//            dependencies {
//                implementation(kotlin("stdlib-jdk8"))
//                implementation(ktor("core"))
//                implementation(ktor("netty"))
//
//                // jackson
//                implementation(ktor("jackson", "serialization"))
//                implementation(ktor("content-negotiation"))
//                implementation(ktor("kotlinx-json", "serialization"))
//                implementation(project(":lib-logging-logback"))
//
//                implementation(ktor("locations"))
//                implementation(ktor("caching-headers"))
//                implementation(ktor("call-logging"))
//                implementation(ktor("auto-head-response"))
//                implementation(ktor("cors"))
//                implementation(ktor("default-headers"))
//                implementation(ktor("cors"))
//                implementation(ktor("auto-head-response"))
//
//                implementation(ktor("websockets"))
//                implementation(ktor("auth"))
//                implementation(ktor("auth-jwt"))
//
//                implementation("ch.qos.logback:logback-classic:$logbackVersion")
//
//                // transport models
//                implementation(project(":common"))
//                implementation(project(":api"))
//                implementation(project(":mappers"))
//                implementation(project(":lib-logging-common"))
//
//                // Stubs
//                implementation(project(":stubs"))
//            }
//        }
//        @Suppress("UNUSED_VARIABLE")
//        val jvmTest by getting {
//            dependencies {
//                implementation(kotlin("test-junit"))
//                implementation(ktor("test-host"))
//                implementation(ktor("content-negotiation", prefix = "client-"))
//                implementation(ktor("websockets", prefix = "client-"))
//                implementation("ch.qos.logback:logback-classic:$logbackVersion")
//                implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")
//            }
//        }
//    }
//    jvmToolchain(17)
//}
//
//tasks {
//    val dockerJvmDockerfile by creating(Dockerfile::class) {
//        group = "docker"
//        from("openjdk:17")
//        copyFile("app.jar", "app.jar")
//        entryPoint("java", "-Xms256m", "-Xmx512m", "-jar", "/app.jar")
//    }
//    create("dockerBuildJvmImage", DockerBuildImage::class) {
//        group = "docker"
//        dependsOn(dockerJvmDockerfile, named("jvmJar"))
//        doFirst {
//            copy {
//                from(named("jvmJar"))
//                into("${project.buildDir}/docker/app.jar")
//            }
//        }
//        images.add("${project.name}:${project.version}")
//    }
//}
//dependencies {
//    implementation(kotlin("stdlib-jdk8"))
//}


val ktorVersion: String by project
val logbackVersion: String by project
val serializationVersion: String by project

fun ktorServer(module: String, version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-server-$module:$version"
fun ktorClient(module: String, version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-client-$module:$version"

plugins {
    kotlin("multiplatform")
    id("io.ktor.plugin")
}

val webjars: Configuration by configurations.creating
dependencies {
    val swaggerUiVersion: String by project
    webjars("org.webjars:swagger-ui:$swaggerUiVersion")
}

//repositories {
//    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
//}

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
////    mainClass.set("ru.otus.otuskotlin.app.ApplicationJvmKt")
}

kotlin {
    jvm { withJava() }

    sourceSets {
        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(ktorServer("core")) // "io.ktor:ktor-server-core:$ktorVersion"

                implementation(project(":common"))
                implementation(project(":api"))
                implementation(project(":mappers"))
                implementation(project(":api-payment-log"))
                implementation(project(":api-subscription-log"))
                implementation(project(":mappers-log"))
                implementation(project(":stubs"))
                implementation(project(":biz"))

                implementation(ktorServer("core")) // "io.ktor:ktor-server-core:$ktorVersion"
                implementation(ktorServer("cio")) // "io.ktor:ktor-server-cio:$ktorVersion"
                implementation(ktorServer("auth")) // "io.ktor:ktor-server-auth:$ktorVersion"
                implementation(ktorServer("auto-head-response")) // "io.ktor:ktor-server-auto-head-response:$ktorVersion"
                implementation(ktorServer("caching-headers")) // "io.ktor:ktor-server-caching-headers:$ktorVersion"
                implementation(ktorServer("cors")) // "io.ktor:ktor-server-cors:$ktorVersion"
                implementation(ktorServer("websockets")) // "io.ktor:ktor-server-websockets:$ktorVersion"
                implementation(ktorServer("config-yaml")) // "io.ktor:ktor-server-config-yaml:$ktorVersion"
                implementation(ktorServer("content-negotiation")) // "io.ktor:ktor-server-content-negotiation:$ktorVersion"
                implementation(ktorServer("websockets")) // "io.ktor:ktor-websockets:$ktorVersion"
                implementation(ktorServer("auth")) // "io.ktor:ktor-auth:$ktorVersion"

                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation(ktorServer("test-host")) // "io.ktor:ktor-server-test-host:$ktorVersion"
                implementation(ktorClient("content-negotiation"))
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))

                implementation(ktorServer("netty")) // "io.ktor:ktor-ktor-server-netty:$ktorVersion"

                // jackson
                implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")

//                implementation(ktorServer("locations"))
                implementation(ktorServer("caching-headers"))
                implementation(ktorServer("call-logging"))
                implementation(ktorServer("auto-head-response"))
                implementation(ktorServer("default-headers")) // "io.ktor:ktor-cors:$ktorVersion"
                implementation(ktorServer("auto-head-response"))

                implementation(ktorServer("auth-jwt")) // "io.ktor:ktor-auth-jwt:$ktorVersion"

//                implementation("ch.qos.logback:logback-classic:$logbackVersion")
                implementation(project(":lib-logging-logback"))

                // transport models
                implementation(project(":common"))
                implementation(project(":api"))
                implementation(project(":mappers"))

                // Stubs
                implementation(project(":stubs"))

                implementation("com.sndyuk:logback-more-appenders:1.8.8")
                implementation("org.fluentd:fluent-logger:0.3.4")
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

tasks {
    @Suppress("UnstableApiUsage")
    withType<ProcessResources>().configureEach {
        println("RESOURCES: ${this.name} ${this::class}")
        from("$rootDir/specs") {
            into("specs")
            filter {
                // Устанавливаем версию в сваггере
                it.replace("\${VERSION_APP}", project.version.toString())
            }
        }
        webjars.forEach { jar ->
//        emptyList<File>().forEach { jar ->
            val conf = webjars.resolvedConfiguration
            println("JarAbsPa: ${jar.absolutePath}")
            val artifact = conf.resolvedArtifacts.find { it.file.toString() == jar.absolutePath } ?: return@forEach
            val upStreamVersion = artifact.moduleVersion.id.version.replace("(-[\\d.-]+)", "")
            copy {
                from(zipTree(jar))
                duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                into(file("${buildDir}/webjars-content/${artifact.name}"))
            }
            with(this@configureEach) {
                this.duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                from(
                    "${buildDir}/webjars-content/${artifact.name}/META-INF/resources/webjars/${artifact.name}/${upStreamVersion}"
                ) { into(artifact.name) }
                from(
                    "${buildDir}/webjars-content/${artifact.name}/META-INF/resources/webjars/${artifact.name}/${artifact.moduleVersion.id.version}"
                ) { into(artifact.name) }
            }
        }
    }
}
