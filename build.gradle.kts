import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.utils.extendsFrom

plugins {
    val kotlinVersion = "1.8.21"

    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"

    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion

    idea
}

group = "com.team"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")

    //kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("io.springfox:springfox-boot-starter:3.0.0")


    //querydsl
    implementation("com.querydsl:querydsl-core:5.0.0")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    annotationProcessor ("com.querydsl:querydsl-apt:5.0.0:jakarta")

    implementation("com.google.code.findbugs:jsr305:3.0.2")

    //validation
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.github.lwaddicor:spring-startup-analysis:1.1.0")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.12")
    //redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    //mysql
    runtimeOnly("com.mysql:mysql-connector-j")

    // jwt
    implementation("com.auth0:java-jwt:3.18.3")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("com.nimbusds:nimbus-jose-jwt:9.31")
    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("io.rest-assured:rest-assured")

    //smtp
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("net.gpedro.integrations.slack:slack-webhook:1.4.0")
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<Test> {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

