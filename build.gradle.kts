plugins {
    id("java")
    jacoco
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.github.bonigarcia:webdrivermanager:6.1.0")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.45.0")

    // Source: https://mvnrepository.com/artifact/io.rest-assured/rest-assured
    testImplementation("io.rest-assured:rest-assured:6.0.0")
    implementation("tools.jackson.core:jackson-databind:3.2.1")

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.assertj:assertj-core:3.27.7")
}

tasks.test {
    useJUnitPlatform()
}