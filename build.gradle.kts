plugins {
    java
    application
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.ortools:ortools-java:9.8.3296")
    testImplementation(platform("org.junit.jupiter:junit-jupiter:5.9.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("com.example.SimpleSolver")
}

java {
    toolchain {
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
