plugins {
    kotlin("jvm")
}
dependencies {
    implementation(project(":gateway"))
    implementation(project(":config"))
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation(project(":usecase"))
    testImplementation(project(":config"))
    testImplementation(project(":core"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}