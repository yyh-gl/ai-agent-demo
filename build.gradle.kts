plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.github.node-gradle.node") version "3.5.1"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// Node configuration for Tailwind CSS
node {
    version.set("18.16.0")
    download.set(true)
    nodeProjectDir.set(file("${project.projectDir}"))
}

// Task to build CSS with Tailwind
tasks.register<com.github.gradle.node.npm.task.NpmTask>("buildCss") {
    description = "Build CSS with Tailwind"
    args.set(listOf("run", "build:css"))
    dependsOn("npmInstall")
}

// Task to watch CSS changes during development
tasks.register<com.github.gradle.node.npm.task.NpmTask>("watchCss") {
    description = "Watch CSS changes with Tailwind"
    args.set(listOf("run", "watch:css"))
    dependsOn("npmInstall")
}

// Make the processResources task depend on buildCss
tasks.named("processResources") {
    dependsOn("buildCss")
}

// Add a bootRun task that watches CSS changes
tasks.register("bootRunWithCssWatch") {
    description = "Run the application with CSS watching"
    dependsOn("bootRun", "watchCss")
}
