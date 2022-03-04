plugins {
    id("build-jar-deps-conventions")
    id("docker-conventions")
}

dependencies {
    implementation(project(":libs:base"))
    implementation(project(":libs:configurations"))
    implementation(project(":libs:crypto-base"))
    implementation(project(":libs:cryptopro"))
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
    with(Deps.HttpClient) {
        implementation(okhttp3)
        implementation(logging)
    }
    with(Deps.Commons) {
        implementation(commonsIo)
        implementation(cli)
    }
}
