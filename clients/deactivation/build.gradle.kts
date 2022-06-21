plugins {
    id("build-jar-deps-conventions")
}

dependencies {
    implementation(project(":libs:base"))
    implementation(project(":libs:configurations"))
    implementation(project(":libs:crypto-base"))
    implementation(project(":libs:jwt"))
    implementation(project(":libs:launcher"))

    with(Deps.Logs) {
        implementation(logbackClassic)
        implementation(logbackJson)
    }
    with(Deps.Jackson) {
        implementation(jacksonDataBind)
        implementation(jacksonAnnotations)
    }
    with(Deps.Commons) {
        implementation(commonsIo)
        implementation(cli)
    }
    with(Deps.HttpClient) {
        implementation(okhttp3)
    }
}
