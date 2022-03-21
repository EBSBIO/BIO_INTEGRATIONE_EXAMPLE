import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED

plugins {
    id("jacoco-conventions")
}

tasks.withType<Test>().configureEach {
    include("**/*Test.class")
    testLogging {
        events = setOf(FAILED)
        exceptionFormat = FULL
    }

    useTestNG() {
        minHeapSize = "512m"
        maxHeapSize = "2048m"
        finalizedBy("codeCoverageReport")
    }
}
