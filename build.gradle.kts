import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc
import com.bmuschko.gradle.docker.tasks.image.*

plugins {
    val kotlinVersion = "1.3.70"
    kotlin("jvm") version kotlinVersion
    id("org.springframework.boot") version "2.2.5.RELEASE"
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("com.google.protobuf") version "0.8.8"
    id("com.bmuschko.docker-remote-api") version "6.1.4"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation ("io.github.lognet:grpc-spring-boot-starter:3.5.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    testCompile("io.projectreactor:reactor-test")
    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
    }

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

sourceSets {
    main {
        java {
            srcDirs("build/generated/source/proto/main/grpc", "build/generated/source/proto/main/java")
        }
    }
}


tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.6.1"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.15.1"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
            }
        }
    }
}

tasks.create("buildImage", DockerBuildImage::class) {
    inputDir.set(file("./"))
    images.add("aygamesco/meter:latest")
}
