import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
}

val koTestSpringExtVer = "1.1.2"

sourceSets {
    create("intTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val jar: Jar by tasks
val bootJar: BootJar by tasks
bootJar.enabled = false
jar.enabled = false

val intTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
}
configurations["intTestRuntimeOnly"].extendsFrom(configurations.testRuntimeOnly.get())

dependencies {
    implementation(project(":application"))
    implementation(project(":gateway"))
    implementation(project(":core"))
    implementation(project(":usecase"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework:spring-tx")
    if (System.getProperty("os.arch") == "aarch64") {
        implementation("io.netty:netty-resolver-dns-native-macos:4.1.79.Final:osx-aarch_64")
    }
    intTestImplementation("org.springframework.boot:spring-boot-starter-test")
    intTestImplementation("io.kotest.extensions:kotest-extensions-spring:$koTestSpringExtVer")
    intTestImplementation("org.springframework.boot:spring-boot-starter-webflux")
}

task<Test>("intTest") {
    description = "Runs integration tests."
    group = "verification"

    testClassesDirs = sourceSets["intTest"].output.classesDirs
    classpath = sourceSets["intTest"].runtimeClasspath

    shouldRunAfter("test")

    useJUnitPlatform()

    systemProperty("spring.profiles.active", "intTest")

    testLogging {
        events("passed")
    }
}
