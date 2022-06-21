package ru.rtlabs.ebs.reference.receiver.client.agree.logging;

public enum Messages {

  START_MODULE("Начало работы модуля для выполнения запросов в AGREE"),
  SEND_REQUEST_ERROR("Ошибка во время отправки запроса по адресу \"{}\" -  \"{}\""),
  SETTING_LOGGING("Подключаем конфигурацию логирования"),
  LOGGING_ENABLED("Логирование подключено"),
  LOGGING_PATH_INVALID("Переданный путь до конфигурации logger-а  \"{}\" невалиден"),
  FAILED_ARGS("Ошибка при разборе аргументов командной строки: аргументы = \" \"{}\"\", \" +\n"
              + "\"сообщение об ошибке = \"{}\""),
  START_CREATE_JWT_FROM_CONFIG("Формируем токен из данных конфигурационного файла"),
  INIT_LOCAL_CRYPTO("Инициализируем локальную работу с криптопровайдером \n"),
  HEADERS_AND_PAYLOAD_FOR_JWT(
      "Передаем для формирования JWT \n HEADERS:  \"{}\", \n PAYLOAD:  \"{}\"\n"),
  JWT_RESULT("Был сформирован следующий JWT: \"{}\"\n"),
  EXISTING_JWT_FROM_CONFIG("Используем готовый токен"),
  CREATE_REQUEST_FOR_AGREE("Формируем запрос для отправки на agree -  \"{}\"\n"),
  EMPTY_RESPONSE_BODY("Не удалось получить тело ответа"),
  GET_RESPONSE("Получен ответ: ответ - \"{}\", тело ответа -  \"{}\""),
  EMPTY_RESPONSE("Получен пустой ответ"),
  URL_ERROR("Не удалось сформировать URL из переданных в конфигурации параметров - \"{}\"");

  public final String message;

  Messages(String message) {
    this.message = message;
  }
}
