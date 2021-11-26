import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    application
}

group = "me.joseluisrf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    /**
     * The kotlin.test library provides annotations to mark test functions and a set of utility functions for
     * performing assertions in tests, independently of the test framework being used.
     *
     * The test framework is abstracted through the Asserter class.A basic Asserter implementation is provided out of
     * the box.Note that the class is not intended to be used directly from tests, use instead the top-level assertion
     * functions which delegate to the Asserter.
     */
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.0")
    testImplementation("io.mockk:mockk:1.12.1")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}