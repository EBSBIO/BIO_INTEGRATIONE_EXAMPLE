import ru.rtlabs.ebs.gradle.tasks.image.Dockerfile

plugins {
    id("java-library-conventions")
}

val groupName = "docker"

val specificBaseImage: String? by project
val baseImage: String by project
val gitCommitHash: String by project
val projectVersion: String by project
val startClass: String by project
val defaultAppDir: String by project
val defaultConfigFilePath: String by project
val defaultLogFilePath: String by project
val defaultAdditionalJarsPath: String? by project

val dockerBuildDir = "${project.buildDir}/docker/"
val allProjectLibsDir = "${rootProject.buildDir}/all-jars"

val createDockerfile = tasks.register<Dockerfile>("createDockerfile") {
    group = groupName

    imageName.set(specificBaseImage ?: baseImage)
    labels.set(mapOf("version" to projectVersion, "git-hash" to gitCommitHash))
    user.set("nbp")
    mainClass.set(startClass)
    configFilePath.set(defaultConfigFilePath)
    logFilePath.set(defaultLogFilePath)
    binDir.set(defaultAppDir)
    additionalJarsPath.set(defaultAdditionalJarsPath ?: "")
}

// копирует runtime зависимости в build/jars папку
val copyRuntimeDependenciesToDockerContext = tasks.register<Copy>("copyRuntimeDependenciesToDockerContext") {
    group = groupName

    val jar: AbstractArchiveTask by tasks
    dependsOn(jar)
    from(configurations.runtimeClasspath, jar.archiveFile)
    into("$dockerBuildDir/jars")
}

// копирует runtime зависимости в build директорию root проекта, с ее помощью можем собрать runtime зависимости
// всех дочерних проектов в одном месте
val copyRuntimeDependenciesToCommonDirectory = tasks.register<Copy>("copyRuntimeDependenciesToCommonDirectory") {
    group = groupName

    val jar: AbstractArchiveTask by tasks

    dependsOn(jar)
    from(configurations.runtimeClasspath, jar.archiveFile)
    into(allProjectLibsDir)
}

// задача, которая генерирует Dockerfile и копирует runtime зависимости приложения,
// после ее выполнения можно запустить docker build build/docker и получить рабочий image приложения.
tasks.register("createDockerBuildContext") {
    group = groupName

    dependsOn(copyRuntimeDependenciesToDockerContext, createDockerfile)
}
