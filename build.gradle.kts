import nu.studer.gradle.credentials.domain.CredentialsContainer

plugins {
    id("nu.studer.credentials")
    `base-conventions`
    `dependency-update-check`
}

val credentials: CredentialsContainer? by project.extra

val gitCommitHash = GitCommitHash(project, "").toString()
val artifactVersion = ArtifactVersion(project).toString()
val projectVersion: String by project

project.ext.set(GIT_COMMIT_HASH, gitCommitHash)
project.ext.set(PROJECT_VERSION, projectVersion)
project.ext.set(ARTIFACT_VERSION, artifactVersion)

allprojects {
    apply(plugin = "java-library-conventions")
    apply(plugin = "testing-conventions")
    apply(plugin = "integration-testing-conventions")
    apply(plugin = "jacoco-conventions")

    group = findProperty("projectGroup")!!
    version = artifactVersion

    tasks.withType<Jar> { duplicatesStrategy = DuplicatesStrategy.INCLUDE }

    repositories {
      mavenLocal()
      mavenCentral()
      gradlePluginPortal()
    }
}
