
# Эталонная реализация

Проект написан с использованием следующих технологий:

    * java 17

Все версии используемых зависимостей можно
посмотреть [тут](buildSrc/src/main/kotlin/ru/rtlabs/ebs/gradle/dependencies/Dependencies.kt)

# Описание

Данный проект предназначен для примера интеграции в ЕБС.
Состав:

- [Эталонная реализация взаимодействия с модулем согласия на обработку (agree)](/clients/agree/README.md)
- [Реализация эталонного запроса на взаимодействие с модулем деактивации УЗ](/clients/deactivation/README.md)
- [Эталонная реализация для модуля regbio (Регистрация БО (С и без шифрования БО))](/clients/regbio/README.md)
- [Эталонная реализация взаимодействия с модулем верификации (pdp) ЕБС](/clients/verification/README.md)
- [Приемник запросов от idp-notifier, matcher-notifier и bioskud-router](/reference-receiver/README.md)
