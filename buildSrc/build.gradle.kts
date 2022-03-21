plugins {
    `kotlin-dsl`
    id("com.github.ben-manes.versions") version "0.41.0" apply false
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false

}

repositories {
    gradlePluginPortal()
    mavenLocal()
}

dependencies {
    implementation("com.github.ben-manes:gradle-versions-plugin:0.41.0")
    implementation("com.github.johnrengelman.shadow:com.github.johnrengelman.shadow.gradle.plugin:7.1.2")
}
