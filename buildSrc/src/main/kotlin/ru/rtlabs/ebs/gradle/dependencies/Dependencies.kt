object Versions {
    const val jvm = "17"
    const val jacoco = "0.8.7"
    const val checkstyle = "10.0"
    const val logback = "1.2.10"
    const val logbackJson = "0.1.5"
    const val vertx = "4.2.5"
    const val jackson = "2.13.1"
    const val commons = "1.4"
    const val commonIo = "2.11.0"
    const val testng = "7.5"
    const val mockitoCore = "4.4.0"
    const val equalsVerifier = "3.9"

}

object Deps {
    object Test {
        const val testng = "org.testng:testng:${Versions.testng}"
        const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCore}"
        const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoCore}"
        const val equalsverifier = "nl.jqno.equalsverifier:equalsverifier:${Versions.equalsVerifier}"

    }
    object Logs {
        const val logbackClassic = "ch.qos.logback:logback-classic:${Versions.logback}"
        const val logbackJson = "ch.qos.logback.contrib:logback-json-classic:${Versions.logbackJson}"
    }

    object Vertx {
        const val vertxCore = "io.vertx:vertx-core:${Versions.vertx}"
        const val vertxWeb = "io.vertx:vertx-web:${Versions.vertx}"
    }

    object Commons {
        const val cli = "commons-cli:commons-cli:${Versions.commons}"
        const val commonsIo = "commons-io:commons-io:${Versions.commonIo}"
    }

    object Jackson {
        const val jacksonDataBind = "com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}"
        const val jacksonAnnotations = "com.fasterxml.jackson.core:jackson-annotations:${Versions.jackson}"
    }
}
