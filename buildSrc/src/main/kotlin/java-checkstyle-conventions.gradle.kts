plugins {
    checkstyle
    java
}

checkstyle {
    toolVersion = Versions.checkstyle
    configFile = rootProject.file("${rootDir}/config/checkstyle/checkstyle.xml")
    maxErrors = 0
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(false)
    }
}
