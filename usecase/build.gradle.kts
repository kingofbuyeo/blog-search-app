plugins {
    kotlin("jvm")
}

val jar: Jar by tasks
val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks
bootJar.enabled = false
jar.enabled = true
jar.archiveClassifier.convention("")

dependencies {
    implementation(project(":core"))
    implementation("org.springframework:spring-tx")
    implementation("org.springframework.retry:spring-retry") // retry
    implementation("org.springframework:spring-orm") // retry exception
}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}