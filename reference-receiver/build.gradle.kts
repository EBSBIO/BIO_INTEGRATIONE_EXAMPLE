plugins{
    id("shadow-jar-conventions")
    id("docker-conventions")
}

dependencies {
    implementation(project(":libs:launcher"))

    with(Deps.Logs) {
        implementation(logbackClassic)
        implementation(logbackJson)
    }
    with(Deps.Jackson) {
        implementation(jacksonDataBind)
        implementation(jacksonAnnotations)
    }
    with(Deps.Vertx) {
        implementation(vertxCore)
        implementation(vertxWeb)
    }
    with(Deps.Commons) {
        implementation(cli)
    }

    with(Deps.Test){
        testImplementation(testng)
        testImplementation(mockitoCore)
        testImplementation(mockitoInline)
        testImplementation(equalsverifier)
    }
}
