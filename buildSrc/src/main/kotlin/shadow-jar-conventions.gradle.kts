import org.gradle.kotlin.dsl.invoke

plugins {
    id("com.github.johnrengelman.shadow")
    id("java-library-conventions")
}

tasks.shadowJar {
    mergeServiceFiles()
}

val startClass: String by project

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = startClass
    }
}
