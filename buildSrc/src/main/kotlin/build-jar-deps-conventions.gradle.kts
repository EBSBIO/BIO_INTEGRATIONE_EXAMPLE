plugins {
    id("java-library-conventions")
}

// копирует runtime зависимости в build/jars папку
tasks.register<Copy>("copyRuntimeDependencies") {
    val jar: AbstractArchiveTask by tasks
    dependsOn(jar)
    from(configurations.runtimeClasspath, jar.archiveFile)
    into("${project.buildDir}/jars")
}
