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
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.14")
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

// Task to run QuadraticSolver
tasks.register<JavaExec>("runQuadraticSolver") {
    group = "application"
    description = "Run the QuadraticSolver example"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.example.QuadraticSolver")
}

