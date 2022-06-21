dependencies {
    with(Deps.Logs) {
        implementation(logbackClassic)
        implementation(logbackJson)
    }
    with(Deps.Jackson) {
        implementation(jacksonDataBind)
    }
    with(Deps.Commons) {
        implementation(cli)
        implementation(commonsIo)
    }
}
