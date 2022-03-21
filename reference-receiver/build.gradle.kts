plugins{
    id("shadow-jar-conventions")
    id("docker-conventions")
}

dependencies {
    with(Deps.Logs) {
        implementation(logbackClassic)
        implementation(logbackJson)
    }
    with(Deps.Jackson) {
        implementation(jacksonAnnotations)
        implementation(jacksonDataBind)
    }
    with(Deps.Vertx) {
        implementation(vertxCore)
        implementation(vertxWeb)
    }
    with(Deps.Commons) {
        implementation(cli)
        implementation(commonsIo)
    }

    with(Deps.Test){
        testImplementation(testng)
        testImplementation(mockitoCore)
        testImplementation(mockitoInline)
        testImplementation(equalsverifier)
    }
}
