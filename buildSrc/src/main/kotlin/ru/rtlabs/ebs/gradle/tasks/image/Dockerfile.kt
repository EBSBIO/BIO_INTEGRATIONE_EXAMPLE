package ru.rtlabs.ebs.gradle.tasks.image

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.jvm.tasks.Jar
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*
import java.util.stream.Collectors
import javax.annotation.Nonnull

abstract class Dockerfile : DefaultTask() {
    @get:Input
    abstract val imageName: Property<String>

    @get:Input
    abstract val labels: MapProperty<String, String>

    @get:Input
    abstract val mainClass: Property<String>

    @get:Input
    abstract val binDir: Property<String>

    @get:Input
    abstract val configFilePath: Property<String>

    @get:Input
    abstract val logFilePath: Property<String>

    @get:Input
    abstract val user: Property<String>

    @OutputFile
    val destFile: RegularFileProperty
    private val runtimeDependencies: Provider<List<String>>
    private val instructions: MutableList<String>

    init {
        runtimeDependencies = project.provider { getRuntimeDependencies() }
        destFile = project.objects.fileProperty()
        project.mkdir(project.layout.buildDirectory.get().file("docker"))
        destFile.set(project.layout.buildDirectory.get().file("docker/Dockerfile"))
        instructions = arrayListOf()
        outputs.cacheIf { false }
        outputs.upToDateWhen { false }
    }

    @TaskAction
    @Throws(IOException::class)
    fun execute() {
        buildInstructions()
        BufferedWriter(FileWriter(destFile.get().asFile)).use { writer ->
            for (instruction in instructions) {
                writer.write(
                    "$instruction\n"
                )
            }
        }
    }

    private fun buildInstructions() {
        from()
        run("echo 'Europe/Moscow' > /etc/timezone")
        run("echo \"exec java \\\$JAVA_OPTS \\\$@\" > run.sh && chmod +rx run.sh")
        copy(addRuntimeDepsSrcDir(), binDir.get())
        run(
            "chmod -R +rx " + binDir.get()
                    + " && if [ -f /bin/busybox ] ; then command=\"adduser -D " + user.get() + "\" ;"
                    + " else command=\"useradd " + user.get() + "\" ;fi && eval \\\$command"
        )
        runAs()
        label()
        entryPoint("sh", "run.sh")
        command(
            "-cp", buildClasspath(),
            "-Dlogback.configurationFile=${logFilePath.get()}",
            mainClass.get(),
            "-config=${configFilePath.get()}"
        )

    }

    private fun getRuntimeDependencies(): List<String> {
        // собираем все jar зависимостей, которые позже добавятся в classpath
        val deps = project.configurations
            .getByName("runtimeClasspath")
            .files
            .stream().map { obj: File -> obj.name }
            .collect(Collectors.toList())
        // собираем jar самого приложения для добавления в classpath
        val jarTaskConfig = project
            .getTasksByName("jar", false)
            .stream()
            .findFirst()
            .orElseThrow {
                GradleException(
                    "Не удалось найти jar таску в списке задач"
                )
            } as Jar
        deps.add(jarTaskConfig.archiveFile.get().asFile.name)
        // сортируем для красоты и возвращаем
        return deps.stream().sorted().collect(Collectors.toList())
    }

    private fun buildClasspath(): String {
        return runtimeDependencies.get().stream().map { e: String -> binDir.get() + e }
            .collect(Collectors.joining(":"))
    }

    private fun addRuntimeDepsSrcDir(): List<String> {
        return runtimeDependencies.get().stream().map { e: String -> "jars/$e" }.collect(Collectors.toList())
    }

    private fun copy(@Nonnull files: List<String>, destination: String) {
        instructions.add("COPY " + java.lang.String.join(" ", files) + ' ' + destination)
    }

    private fun entryPoint(@Nonnull vararg command: String) {
        instructions.add("ENTRYPOINT " + commandArray(*command))
    }

    private fun command(@Nonnull vararg command: String) {
        instructions.add("CMD " + commandArray(*command))
    }

    private fun run(command: String) {
        instructions.add("RUN $command")
    }

    private fun runAs() {
        instructions.add("USER " + user.get())
    }

    private fun label() {
        if (labels.isPresent && labels.get().isNotEmpty()) {
            instructions.add("LABEL " + labels.get().entries
                .stream()
                .map { (key, value): Map.Entry<String, String> -> "$key=$value" }
                .collect(Collectors.joining(" ")))
        }
    }

    private fun from() {
        instructions.add("FROM " + imageName.get())
    }

    companion object {
        private fun commandArray(vararg command: String): String {
            return Arrays.stream(command)
                .map { e: String -> '"'.toString() + e + '"' }
                .collect(Collectors.joining(", ", "[", "]"))
        }
    }
}
