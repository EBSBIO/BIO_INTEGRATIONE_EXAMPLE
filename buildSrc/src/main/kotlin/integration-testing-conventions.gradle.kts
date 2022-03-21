import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("java-library-conventions")
}

val intTest: SourceSet by sourceSets.creating

configurations[intTest.implementationConfigurationName].extendsFrom(configurations.implementation.get())
configurations[intTest.runtimeOnlyConfigurationName].extendsFrom(configurations.runtimeOnly.get())

task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"

    testClassesDirs = intTest.output.classesDirs
    classpath = intTest.runtimeClasspath
    mustRunAfter(tasks.test)

    include("**/*IT.class")
    testLogging {
        events = setOf(TestLogEvent.FAILED)
        exceptionFormat = TestExceptionFormat.FULL
    }
    useTestNG() {
        val suitesFilePath = "src/intTest/testng_it.xml"
        if (file(suitesFilePath).exists()) {
            suites(suitesFilePath)
        }
    }
}

dependencies {
    "intTestImplementation"(project)
}
