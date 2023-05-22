plugins {
    id("java")
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "com.example.server"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    // ORG-Spring libs
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-cache")

    // DB libs
    implementation("org.flywaydb:flyway-core:8.0.5")
    implementation("org.postgresql:postgresql:42.5.4")
    implementation("org.testcontainers:postgresql:1.17.6")

    // Common libs
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    implementation("org.ehcache:ehcache:3.10.6")
    implementation("javax.cache:cache-api:1.1.1")

    // Test libs
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.register("prepareKotlinBuildScriptModel"){}
