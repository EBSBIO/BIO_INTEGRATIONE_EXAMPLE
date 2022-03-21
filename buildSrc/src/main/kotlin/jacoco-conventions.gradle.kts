plugins {
    jacoco
    id("java-library-conventions")
}

jacoco {
    toolVersion = Versions.jacoco
}

task<JacocoReport>("codeCoverageReport") {
    description = "Runs code coverage report."
    group = "verification"

    classDirectories.setFrom(
        sourceSets.main.get().output.classesDirs.files
    )

    executionData.setFrom(allprojects.map {
        it.tasks.withType<Test>()
            .filter { task -> task.the<JacocoTaskExtension>().destinationFile?.exists() ?: false }
            .map { task ->
                {
                    task.the<JacocoTaskExtension>().destinationFile
                }
            }
    })

    sourceDirectories.setFrom(sourceSets.main.get().allJava.srcDirs)

    reports {
        html.required.set(true)
        xml.required.set(true)
        csv.required.set(false)

        xml.outputLocation.set(file("${project.rootProject.buildDir}/reports/jacoco/codeCoverageReport/jacocoTestReport.xml"))
        html.outputLocation.set(file("${project.rootProject.buildDir}/reports/jacoco/codeCoverageReport/html"))
    }
}
