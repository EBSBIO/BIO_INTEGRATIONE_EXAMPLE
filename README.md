# Пример реализаций некоторых АПИ

Проект написан с использованием следующих технологий:

    * java 17
    * vertx 4.2.5

Все версии используемых зависимостей можно
посмотреть [тут](buildSrc/src/main/kotlin/ru/rtlabs/ebs/gradle/dependencies/Dependencies.kt)

# Описание

Пример реализации для 3х АПИ:

- приемка уведомлений о создании УЗ
- приемка уведомлений о мэтчинге УЗ
- приемник шаблонов и деактивации шаблонов процесса "выгрузки БШ"

Проект принимает запрос, записывает в лог полученные данные и формирует успешный ответ.

GET-запрос - проверка работоспособности сервиса: `/api/health`

Обработка уведомлений о создании УЗ: `/api/idp_notifier`

Обработка уведомлений о мэтчинге УЗ: `/api/matcher_notifier`

Обработка запроса деактивации шаблонов: `/api/templates/deactivation`

Обработка запроса приема шаблонов на выгрузку: `/api/templates/upload`

# Алгоритм работы

Сервис принимает в себя [запросы](#Описание-входящих-запросов) и в случае успешного прохождения проверок возвращает
HTTP-ответ 200 OK без HTTP BODY, в противном случае отдает код ошибки и описание, что пошло не так.

# Описание входящих запросов

## Приемка уведомлений о создании УЗ

**Описание**: Передача из ЕБС в ИС Контрагента результата регистрации Пользователя.

URL вызова (callbackUrl) для отправки статуса регистрации пользователя из ЕБС является настроечным параметром IDP и
должен быть предоставлен провайдером идентификации (Банком) при регистрации в ЕБС.

Вызов сервиса: POST {URL Контрагента}

Где {URL контрагента} - URL доступа к API Контрагента, зарегистрированный в ЕБС

Заголовки запроса:

- `Authorization: Bearer {токен от контрагента}`
- `Content-Type: application/json`

**Тело запроса**:

| Наименование параметра | Тип данных | Обязательность | Описание                                                                                                                                                                                                                                                 |
|------------------------|------------|----------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| user_id                | String     | нет            | Идентификатор УЗ пользователя IDP                                                                                                                                                                                                                        | 
| stu                    | String     | да             | Статус результата регистрации пользователя в ЕБС. Принимает значение: <br/>"A" - пользователь успешно зарегистрирован <br/>"B" - пользователь заблокирован <br/>"D" - пользователь деактивирован <br/>"F " - неуспешная попытка регистрации пользователя |

Пример:

```bash
curl --location --request POST 'https://test.idp.ru/send/stu/here' \
--header 'Content-Type: application/json; charset=utf-8' \
--header 'Authorization: Bearer sInNidCI6ImFjY2VzcyIsImFsZyI6IlJT' \
--data-raw '{
"user_id": "00000",
"stu": "A"
}'
```

**Ответ**:

В случае успешного ответа, метод возвращает HTTP-ответ 200 OK без HTTP BODY.

В случае возникновения ошибки при обработке запроса, Система возвращает вызывающей стороне коды ответов HTTP и описания
ошибок в HTTP BODY.

## Приемка уведомлений о мэтчинге УЗ

**Описание**: Передача из ЕБС в ИС Контрагента результата регистрации без БО (мэтчинга) Пользователя.

URL вызова (callbackUrl) для отправки статуса регистрации пользователя из ЕБС является настроечным параметром IDP и
должен быть предоставлен провайдером идентификации (Банком) при регистрации в ЕБС.

**Вызов сервиса**: POST {URL Контрагента}

Где {URL контрагента} - URL доступа к API Контрагента, зарегистрированный в ЕБС

**Заголовки запроса**:

- `Authorization: Bearer {токен от контрагента}`
- `Content-Type: application/json`

**Тело запроса**:

| Наименование параметра | Тип данных | Обязательность | Описание                                                                                                                                                                        |
|------------------------|------------|----------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| description            | String     | нет            | Текст ошибки                                                                                                                                                                    |
| request_id             | String     | да             | Идентификатор запроса                                                                                                                                                           |
| stu                    | String     | да             | Статус результата регистрации пользователя в ЕБС. Принимает значение: <br/>"MA" - пользователь успешно зарегистрирован <br/>"MF" - не успешная попытка регистрации пользователя |
| user_id                | String     | да             |                                                                                                                                                                                 |

Пример ответа при успешной регистрации:

```bash
curl --location --request POST 'https://test.idp.ru/send/stu/here' \
--header 'Content-Type: application/json; charset=utf-8' \
--header 'Authorization: Bearer sInNidCI6ImFjY2VzcyIsImFsZyI6IlJT' \
--data-raw '{
"request_id": "0000000-0000-0000-0000-000000000000",
"user_id": "00000",
"stu": "MA"
}'
```

Пример запроса с не успешным статусом регистрации:

```bash
curl --location --request POST 'https://test.idp.ru/send/stu/here' \
--header 'Content-Type: application/json; charset=utf-8' \
--header 'Authorization: Bearer sInNidCI6ImFjY2VzcyIsImFsZyI6IlJT' \
--data-raw '{
"request_id": "0000000-0000-0000-0000-000000000000",
"user_id": "00000",
"stu": "MF",
"description": "Неверный запрос. Неверный параметр matching"
}'
```

**Ответ**:

В случае успешного ответа, метод возвращает HTTP-ответ 200 OK без HTTP BODY.

В случае возникновения ошибки при обработке запроса, Система возвращает вызывающей стороне коды ответов HTTP и описания
ошибок в HTTP BODY.

## Приемник шаблонов и деактивации шаблонов процесса "выгрузки БШ"

### Выгрузка шаблонов

Вызов сервиса: POST /api/v1/in

Заголовки запроса:

- `Cookie: ebs.session=<transactionId>`
- `Authorization: Bearer`
- `Content-Type: multipart/form-data`

Cookie содержит идентификатор запроса (транзакции), который служит для сопоставления с ранее отправленным запросом на
регистрацию данных.

Тело запроса:

Часть multipart для передачи JWT

| Наименование параметра | Тип данных | Обязательность | Описание      |
|------------------------|------------|----------------|---------------|
| params                 | String     | да             | Содержит JWT. |

Часть multipart для передачи БШ

| Наименование параметра | Тип данных | Обязательность | Описание                                                   |
|------------------------|------------|----------------|------------------------------------------------------------|
| <vendor-name>          | Byte[]     | да             | Название вендора из vectors.vendor из jwt из части params. |

Описание параметров PAYLOAD(часть multipart c JWT):

| Наименование параметра  | Тип данных         | Обязательность | Описание                                                                                                              |
|-------------------------|--------------------|----------------|-----------------------------------------------------------------------------------------------------------------------|
| sub                     | String             | да             | Идентификатор пользователя.                                                                                           |
| aud                     | String             | да             | Имя (мнемоника) проекта.                                                                                              |
| iat                     | Long               | да             | Время создания JWT.                                                                                                   |
| exp                     | Long               | да             | Время протухания JWT.                                                                                                 |
| vectors                 | ARRAY[JSON-OBJECT] | да             | Биометрические контрольные шаблоны.                                                                                   |
| vectors.vendor          | String             | да             | Мнемоника вендора. Содержит в себе название вендора, используемую модальность (фото, звук, т.п.), версия экстрактора. |
| vectors.modality        | String             | да             | Мнемоника модальности (фото, звук и т.п.).                                                                            |
| vectors.signature.data  | String             | да             | Base64 подпись биометрического шаблона (БШ).                                                                          |
| vectors.signature.alg   | String             | да             | Алгоритм ключа для подписи.                                                                                           |
| vectors.signature.type  | String             | да             | Тип подписи.                                                                                                          |
| vectors.signature.keyId | String             | да             | Идентификатор ключа.                                                                                                  |

Пример части токена PAYLOAD:

```json
{
  "sub": "some_user_777",
  "aud": "TEST_IDP",
  "vectors": [
    {
      "vendor": "stub_photo_1.0.0",
      "modality": "photo",
      "signature": {
        "data": "PHoTHHWntJCHl7B5r6Rq7qzOUj9zjg7qQ1xJb3xAtZaG6LtLYxiG1WwlvMRi0IIiiPQWQ0oWzUC+GWedbScbIg==",
        "alg": "GOST3411_2012_256withGOST3410_2012_256",
        "type": "PLAIN",
        "keyId": "immortal-sefsigned-Gost2012-key"
      }
    }
  ],
  "iat": 1646989602,
  "exp": 32257127202
}

```

Пример запроса:

```bash
POST /api/v1/in Cookie: ebs.session=5fc03087-d265-11e7-b8c6-83e29cd24f4c Authorization: Bearer U29tZUJpb1NrdWRUb2tlbgo=
Content-Type: multipart/form-data; boundary=f3URHA_Xnhk0D8gW1iCGLPQk9_gjZr_ywsH Content-Length: {длина тела сообщения}

--f3URHA_Xnhk0D8gW1iCGLPQk9_gjZr_ywsH Content-Disposition: form-data; name="stub_photo_1.0.0"
Content-Type: application/octet-stream

{Поток байт биометрического шаблона. base64 представление:
c29tZSB0ZW1wbGF0ZSBkYXRhOzQ5LjMzMjQ4NywgMTcuNjExNTI1Owo=

----f3URHA_Xnhk0D8gW1iCGLPQk9_gjZr_ywsH Content-Disposition: form-data; name="params"
Content-Type: text/plain

ewogICJ2ZXIiOiAxLAogICJ0eXAiOiAiSldUIiwKICAiYWxnIjogIkdPU1QzNDEwXzIwMTJfMjU2IgoKfQo.eyJzdWIiOiJzb21lX3VzZXJfNzc3IiwiYXVkIjoiVEVTVF9JRFAiLCJ2ZWN0b3JzIjpbeyJ2ZW5kb3IiOiJzdHViX3Bob3RvXzEuMC4wIiwibW9kYWxpdHkiOiJwaG90byIsInNpZ25hdHVyZSI6eyJkYXRhIjoiUEhvVEhIV250SkNIbDdCNXI2UnE3cXpPVWo5empnN3FRMXhKYjN4QXRaYUc2THRMWXhpRzFXd2x2TVJpMElJaWlQUVdRMG9XelVDK0dXZWRiU2NiSWc9PSIsImFsZyI6IkdPU1QzNDExXzIwMTJfMjU2d2l0aEdPU1QzNDEwXzIwMTJfMjU2IiwidHlwZSI6IlBMQUlOIiwia2V5SWQiOiJpbW1vcnRhbC1zZWZzaWduZWQtR29zdDIwMTIta2V5In19XSwiaWF0IjoxNjQ2OTg5NjAyLCJleHAiOjMyMjU3MTI3MjAyfQo.MIAGCSqGSIb3DQEHAqCAMIACAQExDjAMBggqhQMHAQECAgUAMIAGCSqGSIb3DQEHAaCAJIAEggIrZXdvZ0lDSjJaWElpT2lBeExBb2dJQ0owZVhBaU9pQWlTbGRVSWl3S0lDQWlZV3huSWpvZ0lrZFBVMVF6TkRFd1h6SXdNVEpmTWpVMklnb0tmUW8uZXlKemRXSWlPaUp6YjIxbFgzVnpaWEpmTnpjM0lpd2lZWFZrSWpvaVZFVlRWRjlKUkZBaUxDSjJaV04wYjNKeklqcGJleUoyWlc1a2IzSWlPaUp6ZEhWaVgzQm9iM1J2WHpFdU1DNHdJaXdpYlc5a1lXeHBkSGtpT2lKd2FHOTBieUlzSW5OcFoyNWhkSFZ5WlNJNmV5SmtZWFJoSWpvaVVFaHZWRWhJVjI1MFNrTkliRGRDTlhJMlVuRTNjWHBQVldvNWVtcG5OM0ZSTVhoS1lqTjRRWFJhWVVjMlRIUk1XWGhwUnpGWGQyeDJUVkpwTUVsSmFXbFFVVmRSTUc5WGVsVkRLMGRYWldSaVUyTmlTV2M5UFNJc0ltRnNaeUk2SWtkUFUxUXpOREV4WHpJd01USmZNalUyZDJsMGFFZFBVMVF6TkRFd1h6SXdNVEpmTWpVMklpd2lkSGx3WlNJNklsQk1RVWxPSWl3aWEyVjVTV1FpT2lKcGJXMXZjblJoYkMxelpXWnphV2R1WldRdFIyOXpkREl3TVRJdGEyVjVJbjE5WFN3aWFXRjBJam94TmpRMk9UZzVOakF5TENKbGVIQWlPak15TWpVM01USTNNakF5ZlFvAAAAAAAAMYIBXzCCAVsCAQEwgY4wdjELMAkGA1UEBhMCUlUxDzANBgNVBAgMBlJ1c3NpYTEPMA0GA1UEBwwGTW9zY293MRIwEAYDVQQKDAlTdXBlclBsYXQxFTATBgNVBAsMDFN1cGVyUGxhdCBDQTEaMBgGA1UEAwwRU3VwZXJQbGF0IENBIFJvb3QCFHXxzGdg6YQ4wLlz7NCNCmuXo97VMAwGCCqFAwcBAQICBQCgaTAYBgkqhkiG9w0BCQMxCwYJKoZIhvcNAQcBMBwGCSqGSIb3DQEJBTEPFw0yMjAzMTEwOTIyMjNaMC8GCSqGSIb3DQEJBDEiBCAVXDXodopOu-2AzbTDRPkhkN7A5uJuAW8JOxkQplstXDAKBggqhQMHAQEDAgRAJfOT_THXXgJMtulJs6okRxicFT9EZfmcNurN4yU49lO2Tqvo-u0IsZP5m7wh0KDGbu-fnE9VAtg-YU27tNv9dAAAAAAAAA
```

**Коды ответов**:

    200 OK - Вызов метода завершился успешно;
    400 Bad Request - Вызов метода завершился с ошибкой на стороне клиента (вызывающей системы);
    400 Bad Request - Если не прошла проверка подписи JWT.
    401 Unauthorized - Ошибка в случае невозможности авторизовать запрос
    500 Internal Server Error - Вызов метода завершился с ошибкой на стороне сервиса.

### Деактивации пользователя

Вызов сервиса: POST /api/v1/delete

Заголовки запроса:

- `Cookie: ebs.session=<transactionId>`
- `Authorization: Bearer`
- `Content-Type: text/plain`

Cookie содержит идентификатор запроса (транзакции), который служит для сопоставления с ранее отправленным запросом на
регистрацию данных.

Тело запроса:
**Описание Body**
Содержит JWT. Описание параметров PAYLOAD:

| Наименование параметра | Тип данных | Обязательность | Описание                    |
|------------------------|------------|----------------|-----------------------------|
| sub                    | String     | да             | Идентификатор пользователя. |
| aud                    | String     | да             | Имя (мнемоника) проекта.    |
| iat                    | Long       | да             | Время создания JWT.         |
| exp                    | Long       | да             | Время протухания JWT.       |

**Пример запроса:**

Пример JWT.header

```json
{
  "alg": "RS256",
  "typ": "JWT"
}
```

Пример части токена PAYLOAD:

```json
{
  "sub": "extID1",
  "aud": "test_aud",
  "iat": 1643872206,
  "exp": 1644333905
}
```

Пример запроса:

```bash
POST /api/v1/in
Cookie: ebs.session=5fc03087-d265-11e7-b8c6-83e29cd24f4c
Authorization: Bearer U29tZUJpb1NrdWRUb2tlbgo=
Content-Type: text/plain

ewogICJ0eXAiOiAiSldUIiwKICAiYWxnIjogIkdPU1QzNDEwXzIwMTJfMjU2IgoKfQo.eyJzdWIiOiJzb21lX3VzZXJfNzc3IiwiYXVkIjoiVEVTVF9JRFAiLCJpYXQiOjE2NDY5ODk2MDIsImV4cCI6MzIyNTcxMjcyMDJ9Cg.MIAGCSqGSIb3DQEHAqCAMIACAQExDjAMBggqhQMHAQECAgUAMIAGCSqGSIb3DQEHAaCAJIAEgapld29nSUNKMGVYQWlPaUFpU2xkVUlpd0tJQ0FpWVd4bklqb2dJa2RQVTFRek5ERXdYekl3TVRKZk1qVTJJZ29LZlFvLmV5SnpkV0lpT2lKemIyMWxYM1Z6WlhKZk56YzNJaXdpWVhWa0lqb2lWRVZUVkY5SlJGQWlMQ0pwWVhRaU9qRTJORFk1T0RrMk1ESXNJbVY0Y0NJNk16SXlOVGN4TWpjeU1ESjlDZwAAAAAAADGCAV8wggFbAgEBMIGOMHYxCzAJBgNVBAYTAlJVMQ8wDQYDVQQIDAZSdXNzaWExDzANBgNVBAcMBk1vc2NvdzESMBAGA1UECAgwJU3VwZXJQbGF0MRUwEwYDVQQLDAxTdXBlclBsYXQgQ0ExGjAYBgNVBAMMEVN1cGVyUGxhdCBDQSBSb290AhR18cxnYOmEOMC5c-zQjQprl6Pe1TAMBggqhQMHAQECAgUAoGkwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMjIwMzE2MTIwNzQ0WjAvBgkqhkiG9w0BCQQxIgQgqEehJvH_aV2pR6t0sjGFHUIUs6P8wUG7zbav2emuZ5UwCgYIKoUDBwEBAwIEQIYj3wEtDz1CM7grAVOOw7_ca9mhd5IDtN_4I6YdtXLwWLLP2N6vOiYI0tMC8UQU4dUoAWQogX2WZoyKOGKtAfEAAAAAAAA```

Коды ответов:

    200 OK - Вызов метода завершился успешно;
    400 Bad Request - Вызов метода завершился с ошибкой на стороне клиента (вызывающей системы);
    401 Unauthorized - Ошибка в случае невозможности авторизовать запрос
    500 Internal Server Error - Вызов метода завершился с ошибкой на стороне сервиса.

# Описание конфигураций

```json
{
    "server_port": 8092
}
```

server_port - порт, на котором запустится сервис.

# Сборка shadow-jar

Для сборки shadow-jar

```bash
./gradlew shadowJar
```

Запуск:

```bash
java -jar reference-receiver/build/libs/reference-receiver-1.0.0.<hash>-all.jar -c reference-receiver/src/main/resources/properties.json -l reference-receiver/src/main/resources/logback.xml
```

# Сборка образа

Для сборки docker образа

```bash
./gradlew createDockerBuildContext
```

Таска создаст Dockerfile, с помощью которого можно будет собрать образ с сервисом.

Для контейнера необходимо передать конфигурацию по пути:

```bash
/etc/nbp/reference_resiever/
```

Наименование конфигов и путь до них указаны в gradle.properties

```bash
defaultConfigFilePath=/etc/nbp/reference_resiever/properties.json
defaultLogFilePath=/etc/nbp/reference_resiever/logback.xml
```

Пример сборки контейнера:

```bash
cd reference-receiver/build/docker/Dockerfile
docker build -t reference-receiver-container . 
```

Пример запуска контейнера:

```bash
docker run -it --rm -v reference-receiver/src/main/resources:/etc/nbp/reference_resiever reference-receiver-container
```

# Дополнительная информация

[Коллекция запросов для Postman](docs/Примеры-запросов.postman_collection.json)
