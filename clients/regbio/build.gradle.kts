plugins{
    id("build-jar-deps-conventions")
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

    with(Deps.HttpClient) {
        implementation(okhttp3)

    }
    implementation(project(":libs:base"))
    implementation(project(":libs:configurations"))
    implementation(project(":libs:crypto-base"))
    implementation(project(":libs:jwt"))
    implementation(project(":libs:launcher"))
}
