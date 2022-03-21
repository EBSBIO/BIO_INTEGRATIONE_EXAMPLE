import org.gradle.api.Project
import org.gradle.kotlin.dsl.provideDelegate
import java.io.File

private const val VERSION_RELEASE = "VERSION_RELEASE"

const val ARTIFACT_VERSION = "artifactVersion"
const val GIT_COMMIT_HASH = "gitCommitHash"
const val PROJECT_VERSION = "projectVersion"

data class GitCommitHash(
    val project: Project,
    val default: String
) {
    private val gitHash: String by lazy {
        val process = "git rev-parse --verify --short=8 HEAD".execute(null, project.rootDir)
        if (process.waitFor() == 0) process.text.trim()
        else default
    }

    override fun toString(): String = gitHash
}

fun String.execute(envp: Array<String>?, workingDir: File?): Process =
    Runtime.getRuntime().exec(this, envp, workingDir)

val Process.text: String
    get() = inputStream.bufferedReader().readText()

data class ArtifactVersion(
    val project: Project,
) {
    private val artifactVersion: String by lazy {
        val projectVersion: String by project
        val hashCommit = GitCommitHash(project, "").toString()

        if (System.getenv(VERSION_RELEASE) == null || System.getenv(VERSION_RELEASE).isEmpty()) {
            if (hashCommit.isNotEmpty()) {
                "${projectVersion}.${hashCommit}"
            } else {
                projectVersion
            }
        } else {
            System.getenv(VERSION_RELEASE)
        }
    }

    override fun toString(): String = artifactVersion
}
