dependencies {
    implementation(project(":libs:base"))
    implementation(project(":libs:configurations"))
    implementation(project(":libs:crypto-base"))
    implementation(project(":libs:cryptopro"))

    with(Deps.Cryptopro) {
        implementation(jcsp)
        implementation(jcryptop)
    }
    with(Deps.Logs) {
        implementation(logbackClassic)
        implementation(logbackJson)
    }
    with(Deps.Jackson) {
        implementation(jacksonDataBind)
        implementation(jdk8)
        implementation(jsr310)
    }

}
