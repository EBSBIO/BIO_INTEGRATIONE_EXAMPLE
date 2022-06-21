dependencies {
    with(Deps.Jackson) {
        implementation(jacksonAnnotations)
        implementation(jacksonCore)
        implementation(jacksonDataBind)
    }
}
