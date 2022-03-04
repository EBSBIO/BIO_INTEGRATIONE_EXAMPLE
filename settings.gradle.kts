pluginManagement {
    plugins {
        id("nu.studer.credentials") version "3.0" apply false
    }
}

include(":reference-receiver")

include(
    ":clients:verification",
    ":clients:deactivation",
    ":clients:agree",
    ":clients:regbio"
)

include(
    ":libs:launcher",
    ":libs:jwt",
    ":libs:base",
    ":libs:configurations",
    ":libs:cryptopro",
    ":libs:crypto-base",
)
