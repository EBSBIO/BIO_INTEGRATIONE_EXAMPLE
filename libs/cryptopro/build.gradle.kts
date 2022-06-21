dependencies {
    implementation(project(":libs:crypto-base"))
    implementation(project(":libs:base"))
    implementation(project(":libs:configurations"))

    with(Deps.Cryptopro) {
        implementation(jcp)
        implementation(jcsp)
    }
    with(Deps.Logs) {
        implementation(logbackClassic)
        implementation(logbackJson)
    }
    with(Deps.Bouncycastle) {
        implementation(bcprovJdk15on)
        implementation(bcpkixJdk15on)
    }
}
