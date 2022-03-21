package ru.rtlabs.ebs.reference.receiver.logging;

public enum Messages {
  INIT_SERVER("Запуск сервера на порту \"{}\""),
  FAILED_TO_STOP_SERVER("Не удалось остановить сервер: \"{}\""),
  START_SERVER("Запуск http сервера..."),
  SEND_FAILED_RESPONSE("Отправляем результат об ошибке: \"{}\""),
  PARSING_BODY("Парсим тело запроса"),
  PARSING_BODY_BIOSKUD_ROUTER_REQUEST("Парсим полученный JWT при запросе на выгрузку/деактивацию"),
  CHECK_VECTORS("Проверка валидности полученных векторов"),
  GET_JWT("Получен JWT: \"{}\""),
  ERROR_PARSE_REQUEST("Получен невалидный запрос"),
  ERROR_EMPTY_BODY("Получено пустое тело запроса"),
  ERROR_JWT_PART_SIZE("Получен невалидный JWT. Неожидаемое кол-во частей."),
  ERROR_JWT_IS_EMPTY("Получен пустой JWT."),
  ERROR_JWT_SIGN_EMPTY("Получен невалидный JWT. Пустая подпись."),
  ERROR_INVALID_VECTORS("Получен невалидный запрос. Отсутствует вектор \"{}\"."),
  CHECK_JWT_VALUE("Выполняется проверка содержимого JWT."),
  GET_REQUEST("Получено тело запроса: \"{}\""),
  SUCCESS_RESPONSE("Обработка прошла успешно"),
  NOT_FOUND_FILES("Не переданы файлы"),
  NOT_FOUND_JWT("Не передан токен доступа"),
  UNAUTHORIZED("Ошибка авторизации пользователя."),
  INVALID_JWT("Получен невалидный jwt."),
  LARGE_FILE_SIZE("Максимальный размер превышен: %s > %s"),
  UPLOADING("uploadingData: {}"),
  INFO_GET_FILE("Получен файл: {}"),
  CHUNK_PROCESSING_ERROR("Ошибка обработки chunk-а файла"),
  PARTS_LOADED_AS_FILES("Следующие части multipart загружены как файлы: {}"),
  ENCODE_TO_WIRE_UNSUPPORTED("Метод encodeToWire(Buffer buffer, T s) не поддерживается"),
  DECODE_FROM_WIRE_UNSUPPORTED("Метод decodeFromWire(int pos, Buffer buffer) не поддерживается"),
  ERROR_COOKIE_EBS_SESSION_NOT_FOUND(
      "Ошибка обработки запроса. Cookie с именем \"ebs.session\" не найдены"),
  ERROR_UPLOADING_FILE("Ошибка при загрузке файла"),

  CREATE_TRANSACTION_ID("Создан идентификатор транзакции"),
  EXCEPT_HTTP_RETURN("Отвечаем на заголовок Except: 100 continue"),
  PUB_SUB_PATTERN_INFO("pattern {}, channel {}, message {}"),
  SEND_TO_EVENT_BUS("Отправляем следующее сообщение в eventBus по адресу {} - {}"),

  SENDING_ERROR_FILE_WERE_NOT_LOADED("Ранее произошли ошибки при загрузке файла"),
  GOT_N_PARTS_FROM_UPLOAD("Загрузили {} частей multipart"),

  SETTING_LOGGING("Подключаем конфигурацию логирования"),
  LOGGING_ENABLED("Логирование подключено"),
  LOGGING_PATH_INVALID("Переданный путь до конфигурации logger-а '{}' невалиден"),
  FAILED_ARGS("Ошибка при разборе аргументов командной строки: аргументы = \"{}\", \" +\n"
              + "          \"сообщение об ошибке = \"{}\""),


  WRONG_INPUT("Неверный формат тела запроса");

  public final String message;

  Messages(String message) {
    this.message = message;
  }
}
