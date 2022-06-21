dependencies {
    implementation(project(":libs:base"))
    implementation(project(":libs:configurations"))

    with(Deps.Logs) {
        implementation(logbackClassic)
        implementation(logbackJson)
    }
    with(Deps.Jackson) {
        implementation(jacksonDataBind)
        implementation(jacksonAnnotations)
    }
    with(Deps.Bouncycastle) {
        implementation(bcprovJdk15on)
        implementation(bcpkixJdk15on)
    }

    with(Deps.Cryptopro) {
        implementation(cades)
    }
}
