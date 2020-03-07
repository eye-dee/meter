import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    val kotlinVersion = "1.3.70"
    kotlin("jvm") version kotlinVersion
    id("org.springframework.boot") version "2.1.2.RELEASE"
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("com.google.protobuf") version "0.8.8"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.grpc:grpc-netty:1.16.1")
    implementation("io.grpc:grpc-protobuf:1.16.1")
    implementation("io.grpc:grpc-stub:1.16.1")

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