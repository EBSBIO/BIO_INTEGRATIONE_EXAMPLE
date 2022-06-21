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
    const val bouncycastle = "1.50"
    const val jetbrains = "23.0.0"
    const val cryptopro = "5.0.40424"
    const val httpOk3 = "4.9.3"
}

object Deps {
    object Test {
        const val testng = "org.testng:testng:${Versions.testng}"
        const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCore}"
        const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoCore}"
        const val equalsverifier = "nl.jqno.equalsverifier:equalsverifier:${Versions.equalsVerifier}"
    }

    object Cryptopro {
        const val jcp = "ru.cryptopro.jcp:jcp:${Versions.cryptopro}"
        const val jcsp = "ru.cryptopro.jcp:jcsp:${Versions.cryptopro}"
        const val cades = "ru.cryptopro:CAdES:${Versions.cryptopro}"
        const val jcryptop = "ru.cryptopro:JCryptoP:${Versions.cryptopro}"
    }

    object Logs {
        const val logbackClassic = "ch.qos.logback:logback-classic:${Versions.logback}"
        const val logbackJson = "ch.qos.logback.contrib:logback-json-classic:${Versions.logbackJson}"
    }

    object HttpClient {
        const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.httpOk3}"
        const val logging = "com.squareup.okhttp3:logging-interceptor:${Versions.httpOk3}"
    }

    object Vertx {
        const val vertxCore = "io.vertx:vertx-core:${Versions.vertx}"
        const val vertxWeb = "io.vertx:vertx-web:${Versions.vertx}"
    }

    object Commons {
        const val cli = "commons-cli:commons-cli:${Versions.commons}"
        const val commonsIo = "commons-io:commons-io:${Versions.commonIo}"
    }

    object Bouncycastle {
        const val bcprovJdk15on = "org.bouncycastle:bcprov-jdk15on:${Versions.bouncycastle}"
        const val bcpkixJdk15on = "org.bouncycastle:bcpkix-jdk15on:${Versions.bouncycastle}"
    }

    object Jackson {
        const val jacksonDataBind = "com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}"
        const val jacksonAnnotations = "com.fasterxml.jackson.core:jackson-annotations:${Versions.jackson}"
        const val jacksonCore = "com.fasterxml.jackson.core:jackson-core:${Versions.jackson}"
        const val jdk8 = "com.fasterxml.jackson.datatype:jackson-datatype-jdk8:${Versions.jackson}"
        const val jsr310 = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jackson}"
    }

    object Jetbrains {
        const val annotations = "org.jetbrains:annotations:${Versions.jetbrains}"
    }
}
