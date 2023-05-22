plugins {
    id("java")
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "com.example.client"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    // ORG-Spring libs
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Common libs
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.register("prepareKotlinBuildScriptModel"){}
