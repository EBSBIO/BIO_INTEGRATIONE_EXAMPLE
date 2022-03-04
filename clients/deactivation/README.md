# Реализация эталонного запроса на взаимодействие с модулем деактивации УЗ

Проект написан с использованием следующих технологий:

    * java 17

## Описание

Приложение является реализацией эталонного запроса на деактивацию УЗ пользователя. 

При запуске приложения происходит отправка DELETE REST-запроса на адрес модуля *деактивации* {host}/api/v3/users/deactivate-acc. 

### Подготовка

Для деактивации требуется, чтобы УЗ была зарегистрирована

Пример JWT.header

```json
{
  "typ": "JWT",
  "alg": "GOST3410_2012_256"
}
```

Пример части токена PAYLOAD:

```json
{
  "service_type": "reg-bio",
  "quality_id": 4,
  "datetime_tz": 1618404859,
  "infosystem": {
    "system_id": "system_id",
    "contract_id": "contract_id",
    "ra_id": "ra_id",
    "employee_id": "employee_id",
    "cert_id": "cert_id"
  },
  "agree": {
    "agreement_id": "agreement_id-agreeID",
    "date_from": 1589277386,
    "date_to": 1683868229
  },
  "person": {
    "idp": "idp-idp",
    "user_id": "user_id",
    "contact": {
      "phone": "+71234567890",
      "email": "example@example.com"
    }
  },
  "bio_collecting": [
    {
      "name": "bs1",
      "modality": "photo",
      "bio_sample_signature": "bio_sample_signature"
    },
    {
      "name": "bs2",
      "bio_sample_signature": "bio_sample_signature",
      "modality": "sound",
      "bio_metadata": {
        "voice_1_start": "00.000",
        "voice_1_end": "09.699",
        "voice_1_desc": "digits_asc",
        "voice_2_start": "10.172",
        "voice_2_end": "18.834",
        "voice_2_desc": "digits_asc",
        "voice_3_start": "19.230",
        "voice_3_end": "27.784",
        "voice_3_desc": "digits_asc"
      }
    }
  ],
  "metrics": {
    "total_reg_time_end": "2020-03-30T17:30:09.453+0500",
    "new_client_time_start": "2020-03-30T17:30:09.453+0500",
    "new_client_time_end": "2020-03-30T17:30:09.453+0500",
    "consent_time_start": "2020-03-30T17:30:09.453+0500",
    "consent_time_end": "2020-03-30T17:30:09.453+0500",
    "photo_time_start_1": "2020-03-30T17:30:09.453+0500",
    "photo_time_end_1": "2020-03-30T17:30:09.453+0500",
    "front_bqc_estimators_photo_1": "2020-03-30T17:30:09.453+0500",
    "sound_direct_time_start_1": "2020-03-30T17:30:09.453+0500",
    "sound_direct_time_end_1": "2020-03-30T17:30:09.453+0500",
    "front_bqc_estimators_sound_direct_1": "2020-03-30T17:30:09.453+0500",
    "sound_reverse_time_start_1": "2020-03-30T17:30:09.453+0500",
    "sound_reverse_time_end_1": "2020-03-30T17:30:09.453+0500",
    "front_bqc_estimators_sound_reverse_1": "2020-03-30T17:30:09.453+0500",
    "sound_random_time_start_1": "2020-03-30T17:30:09.453+0500",
    "sound_random_time_end_1": "2020-03-30T17:30:09.453+0500",
    "front_bqc_estimators_sound_random_1": "2020-03-30T17:30:09.453+0500",
    "sound_all_time_end_1": "2020-03-30T17:30:09.453+0500",
    "front_bqc_estimators_sound_all_1": "2020-03-30T17:30:09.453+0500",
    "bank_find_profile_time_start_1": "2020-03-30T17:30:09.453+0500",
    "bank_find_profile_time_end_1": "2020-03-30T17:30:09.453+0500",
    "esia_find_account_msg_id": "2020-03-30T17:30:09.453+0500",
    "esia_confirm_msg_id": "2020-03-30T17:30:09.453+0500",
    "esia_register_by_simplified_msg_id": "2020-03-30T17:30:09.453+0500",
    "esia_recover_msg_id": "2020-03-30T17:30:09.453+0500",
    "name_equipment_camera": "2020-03-30T17:30:09.453+0500",
    "name_equipment_microphone": "2020-03-30T17:30:09.453+0500"
  }
}
```

Пример запроса:

```
POST /api/v3/regBio
Content-Type: multipart/form-data; boundary={data}
Content-Length: {длина тела сообщения}
   
 --{data}
Content-Disposition: form-data; name="bs1", filename="xxx.jpg"
Content-Type: image/jpeg
    
 {Поток байт биометрического образца}
   
----{data}
Content-Disposition: form-data; name="bs2", filename="xxx.wav"
Content-Type: audio/wav
    
 {Поток байт биометрического образца}
   
----{data}
Content-Disposition: form-data; name="params"
Content-Type: application/octet-stream
   
 {JWT токен}
```  

### Деактивация

Сервис выполняет деактивацию УЗ. В файле properties.json требуется настроить следующие параметры

| Наименование параметра | Тип данных | Обязательность | Описание                                                                                    |
|------------------------|------------|----------------|---------------------------------------------------------------------------------------------|
| container_type         | String     | да             | Тип контейнера.                                                                             |
| key_id                 | String     | да             | Идентификатор открытого ключа.                                                              |
| password               | String     | да             | Пароль.                                                                                     |
| algorithm              | String     | да             | Алгоритм формирования электронной подписи.                                                  |
| is_jwt_config          | boolean    | да             | Формирование jwt из конфигов (true) или из параметров (false).                              |
| request_id_value       | String     | нет            | Уникальный идентификатор сообщения, сохраняется неизменным для всех сообщений транзакции.   |
| host                   | String     | да             | Адрес сервиса деактивации УЗ.                                                               |
| ver                    | int        | да             | Версия токена доступа.                                                                      |
| typ                    | String     | да             | Тип токена.                                                                                 |
| alg                    | String     | да             | Алгоритм формирования электронной подписи.                                                  |
| sbt                    | String     | да             | Тип маркера доступа.                                                                        |
| iss                    | String     | да             | Поле issuer по нему определяется что за idp пришёл на сервис деактивации.                   |
| sub                    | String     | да             | Идентификатор объекта.                                                                      |
| cert_id                | String     | да             | Идентификатор сертификата.                                                                  |
| jwt                    | String     | да             | Сформированный jwt токен доступа.                                                           |
| nbf                    | String     | да             | Время в формате Unix Time, определяющее момент, когда токен станет валидным (not before).   |
| exp                    | String     | да             | Время в формате Unix Time, определяющее момент, когда токен станет невалидным (expiration). |
| iat                    | String     | да             | Время в формате Unix Time, определяющее момент, когда токен был создан.                     |

Пример параметров:

```json
{
  "crypto_config": {
    "container_type": "HDIMAGE",
    "key_id": "TestKeyId",
    "password": "123",
    "algorithm": "GOST3411_2012_256withGOST3410_2012_256"
  },
  "base": {
    "is_jwt_config": "{Формирование jwt из конфигов (true) или из параметров (false)} - обязательно. Пример: false",
    "request_id": "{Уникальный идентификатор сообщения, сохраняется неизменным для всех сообщений транзакции} - обязательно. Пример: request_id_value",
    "host": "{Адрес сервиса деактивации УЗ} - обязательно. Пример: http://test.host"
  },
  "create_session_jwt": {
        "jwt": "{Сформированный jwt токен доступа} - обязательно. Пример: eyJ2ZXIiOjEsInR5cCI6IkpXVCIsInNidCI6ImFjY2VzcyIsImFsZyI6IkdPU1QzNDEwXzIwMTJfMjU2In0.eyJzdWIiOiIxMjM0IiwiaXNzIjoiaHR0cDovL3Rlc3QuaXNzIiwiY2VydF9pZCI6ImNlcnRfaWRfdmFsdWUiLCJleHAiOjE2OTQ3Mjk0MzQsImlhdCI6MTY1NDcyNTM0MiwibmJmIjoxNjU0NzI1MzQyfQ.uVLJyIFjG_sh3byZUsW1D_bbx6HwltHOuyjNvCJHzPlPJ596LsDD4XViiWxACjqGAD4e-5q5CFr3D6xw9MC6lw"
  },
  "create_session_jwt_data": {
    "header": {
      "ver": "{Версия токена доступа} - необязательно. Пример: 1",
      "typ": "{Тип токена} - обязательно. Пример: JWT",
      "sbt": "{Тип токена} - необязательно. Пример: access",
      "alg": "{Тип маркера доступа} - обязательно. Пример: GOST3410_2012_256"
    },
    "payload": {
      "iss": "{Поле issuer по нему определяется что за idp пришёл на сервис деактивации} - обязательно. Пример: http://test.iss",
      "sub": "{Идентификатор объекта} - обязательно. Пример: 1234",
      "cert_id": "{Идентификатор сертификата} - обязательно. Пример: cert_id_value",
      "nbf": "{Время в формате Unix Time, определяющее момент, когда токен станет валидным (not before)} - необязательно. Пример: 1654725342",
      "exp": "{Время в формате Unix Time, определяющее момент, когда токен станет невалидным (expiration)} - обязательно. Пример: 1694729434",
      "iat": "{Время в формате Unix Time, определяющее момент, когда токен был создан} - обязательно. Пример: 1654725342"
    }
  }
}
```

### Примечание

##### Данные для формирования jwt:

Header:

```json
{
  "typ": "JWT",
  "alg": "GOST3410_2012_256"
}
```

Payload:

```json
{
  "sub": "1234",
  "iss": "http://test.iss",
  "cert_id": "cert_id_value",
  "exp": 1694729434,
  "iat": 1654725342,
  "nbf": 1654725342
}
```


Пример запроса:

```
curl --location --request DELETE 'http://test.iss/api/v3/users/deactivate-acc' \
--header 'X-EBS-TraceProcess: true' \
--header 'Cookie: ebs.session=request_id_value' \
--header 'Authorization: Bearer eyJ2ZXIiOjEsInR5cCI6IkpXVCIsInNidCI6ImFjY2VzcyIsImFsZyI6IkdPU1QzNDEwXzIwMTJfMjU2In0.eyJzdWIiOiIxMjM0IiwiaXNzIjoiaHR0cDovL3Rlc3QuaXNzIiwiY2VydF9pZCI6ImNlcnRfaWRfdmFsdWUiLCJleHAiOjE2OTQ3Mjk0MzQsImlhdCI6MTY1NDcyNTM0MiwibmJmIjoxNjU0NzI1MzQyfQ.uVLJyIFjG_sh3byZUsW1D_bbx6HwltHOuyjNvCJHzPlPJ596LsDD4XViiWxACjqGAD4e-5q5CFr3D6xw9MC6lw'
```

# Сборка jar и запуск

Из-за особенностей работы с cryptopro необходимо собирать jar без зависимостей.

Для сборки jar необходимо выполнить:

```bash
./gradlew build
```

Для сборки зависимостей для проекта необходимо вызвать таску для сбора зависимостей:

```bash
./gradlew copyRuntimeDependencies
```

Для запуска необходима специальная настройка стенда и java csp, для работы с cryptopro. Пример команды проекта:

```bash
java -cp clients/deactivation/build/libs/deactivation-1.0.1.<hash>.jar:/home/java-csp-5.0.40424/*:clients/deactivation/build/jars/* ru.rtlabs.ebs.reference.receiver.client.deactivation.DeactivationMain -c clients/deactivation/src/main/resources/properties.json -l clients/deactivation/src/main/resources/logback.xml
```


