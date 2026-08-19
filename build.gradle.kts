plugins {
    id("java")
    jacoco
    id("io.qameta.allure") version "4.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

val allureVersion = "2.35.3"

repositories {
    mavenCentral()
}

dependencies {
    // Allure
    testImplementation("io.qameta.allure:allure-assertj")
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-rest-assured")
    testImplementation("io.qameta.allure:allure-junit5")

    //UI-test
    testImplementation("io.github.bonigarcia:webdrivermanager:6.1.0")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.45.0")

    //API-test
    testImplementation("io.rest-assured:rest-assured:6.0.0")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("io.github.cdimascio:dotenv-java:3.2.0")

    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.assertj:assertj-core:3.27.7")
}

tasks.test {
    useJUnitPlatform()
}