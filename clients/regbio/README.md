# Эталонная реализация для модуля regbio (Регистрация БО (С и без шифрования БО))

Проект написан с использованием следующих технологий:

    * java 17

Все версии используемых зависимостей можно
посмотреть [тут](../../buildSrc/src/main/kotlin/ru/rtlabs/ebs/gradle/dependencies/Dependencies.kt)

# Описание

Эталонная реализация для 2х API модуля regbio: Отправка API запроса на /api/v3/regBio в случае отправки нешифрованных БО,
и /api/v3/regEncryptedBio в случае отправки шифрованного БО. Оба запроса - POST.

Обязательные поля для заполнения в файле properties.json:

    * baseUrl - адрес сервера, к которому будет направлен запрос
    * regBioEndpoint - API-endpoint вызывающийся при сценарии отправки БО без шифрования
    * regEncryptedBioEndpoint - API-endpoint вызывающийся при сценарии отправки зашифрованных БО
    * useJwtFromConfig - Если true, то при отправке будет использовано JWT, указанное в поле payLoad. 
                         Если false, то JWT будет формироваться с использованием данных, указанных пользователем.
    * encrypt - Если true, то запрос будет отправлен на regEncryptedBioEndpoint предварительно зашифровав БО.
                Если false, то запрос будет отправлен на regBioEndpoint без шифрования БО.
    * imageName - путь до файла с Фотографией.
    * soundName - путь до файла с Аудио.
    * imagePartName - наименование парта с Фотографией.
    * soundPartName - наименование парта с Аудио.
    * payloadHeaderPath - путь до файла с хэдэрами запроса.
    * payloadBodyPath - путь до файла с основной информацией о БО
    * encImageContentType - тип шифрованной Фотографии
    * encSoundContentType - тип шифрованного Аудио
    * imageContentType - тип нешифрованной Фотографии
    * soundContentType - тип нешифрованного Аудио
    * crypto_config - Основная информация для КриптоПро, узазываемая для успешного получения JWT
    * payLoad - готовый JWT токен.


# Алгоритм работы

Сервис принимает в себя properties.json файл, в котором указаны настройки для будущего запроса в сервис regbio.
в файле regbio/src/main/java/config/RegbioConfig.java описан функционал каждого поля.

# Сборка jar и запуск

Из-за особенностей работы с cryptopro необходимо собирать jar без зависимостей.

Для сборки jar необходимо выполнить:

```bash
./gradlew build
```

Для сборки зависимостей проекта необходимо вызвать команду:

```bash
./gradlew copyRuntimeDependencies
```

Для запуска необходима специальная настройка стенда и java csp, для работы с cryptopro. Пример команды запуска проекта:

```bash
java -cp clients/regbio/build/libs/agree-1.0.1.<hash>.jar:/home/java-csp-5.0.40424/*:clients/regbio/build/jars/* ru.rtlabs.ebs.regbio.MainThread -c clients/regbio/src/main/resources/properties.json -l clients/agree/src/main/resources/logback.xml
```

`<hash>` - фрагмент версии, обозначающий текущее положение в системе контроля версий.

`logback.xml` - конфигурация логирования.

`general-properties.json` - конфигурация для модуля.

**Коды ответов**:

    202 Accepted - Вызов метода завершился успешно;
    400 Bad Request - Вызов метода завершился с ошибкой на стороне клиента (вызывающей системы);
    403 Forbidden - Отказано в доступе
    500 Internal Server Error - Вызов метода завершился с ошибкой на стороне сервиса.

