plugins {
    kotlin("jvm")
}


val jar: Jar by tasks
val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks
bootJar.enabled = false
jar.enabled = true
jar.archiveClassifier.convention("")

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
