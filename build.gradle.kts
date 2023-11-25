plugins {
    id("org.springframework.boot") version "3.1.5" apply false
    id("io.spring.dependency-management") version "1.1.4" apply false
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22" apply false
    kotlin("plugin.jpa") version "1.7.22" apply false
    kotlin("plugin.serialization") version "1.7.22" apply false
}

group = "com.yongchul"
version = "0.0.1-SNAPSHOT"
val jar: Jar by tasks
jar.enabled = false

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17
repositories {
    mavenCentral()
}

val koTestVer = "5.5.4"

subprojects {
    repositories {
        mavenCentral()
    }
    group = "com.yongchul"
    version = "0.0.1-SNAPSHOT"

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = "17"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

    dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        runtimeOnly("org.springframework.boot:spring-boot-properties-migrator")
        testImplementation("io.kotest:kotest-runner-junit5:$koTestVer")
        testImplementation("io.kotest:kotest-assertions-core:$koTestVer")
        testImplementation("io.kotest:kotest-property:$koTestVer")
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}