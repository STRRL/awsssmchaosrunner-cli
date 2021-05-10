import org.gradle.jvm.tasks.Jar

plugins {
    java
    kotlin("jvm") version "1.4.32"
}

group = "org.chaosmesh"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.amazonaws:aws-java-sdk-ssm:1.+")
    implementation("software.amazon.awsssmchaosrunner:awsssmchaosrunner:1.3.0")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.6")
    implementation("info.picocli:picocli:4.6.1")
    implementation("org.apache.logging.log4j:log4j-api:2.+")
    implementation("org.apache.logging.log4j:log4j-core:2.+")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.+")
    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}


val fatJar = task("fatJar", type = Jar::class) {
    archiveBaseName.set("${project.name}-fat")
    manifest {
        attributes["Implementation-Title"] = "Gradle Jar File Example"
        attributes["Implementation-Version"] = archiveVersion
        attributes["Main-Class"] = "org.chaosmesh.runner.MainKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}