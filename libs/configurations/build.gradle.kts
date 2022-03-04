dependencies {
    with(Deps.Jackson) {
        implementation(jacksonAnnotations)
        implementation(jacksonCore)
    }
    with(Deps.Jetbrains) {
        implementation(Deps.Jetbrains.annotations)
    }
}
