plugins {
    `java-library`
    java
    id("base-conventions")
    id("java-checkstyle-conventions")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.compileJava {
    // See: https://docs.oracle.com/en/java/javase/12/tools/javac.html
    options.compilerArgs.addAll(
        listOf(
            "-Xlint:all", // Enables all recommended warnings.
        )
    )
}

tasks.compileTestJava {
    // See: https://docs.oracle.com/en/java/javase/12/tools/javac.html
    options.compilerArgs.addAll(
        listOf(
            "-Xlint", // Enables all recommended warnings.
            "-Xlint:-overrides", // Disables "method overrides" warnings.
            "-parameters" // Generates metadata for reflection on method parameters.
        )
    )
}

java {
    sourceCompatibility = JavaVersion.toVersion(Versions.jvm)
    targetCompatibility = JavaVersion.toVersion(Versions.jvm)
}
