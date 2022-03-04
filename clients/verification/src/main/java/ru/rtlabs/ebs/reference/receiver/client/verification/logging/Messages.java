package ru.rtlabs.ebs.reference.receiver.client.verification.logging;

public enum Messages {
  EXTENDED_RESULT_GET_SUCCESS("Получен расширенный результат: \"{}\""),
  SEND_REQUEST("Отправляем запрос \"{}\""),
  VERIFICATION_TOKEN_GET_SUCCESS("Получен верификационный токен: \"{}\""),
  REQUEST_RESULT("Результат выполнения запроса: body \"{}\", code \"{}\""),
  MULTIPART_COUNT("Количество частей мультипарта: \"{}\""),
  SEND_REQUEST_CREATE_SESSION("Отправляем запрос на создание сессии пользователя в ЕБС"),
  SEND_REQUEST_RESULT("Отправляем запрос на получение результата верификации в ЕБС"),
  SEND_REQUEST_GET_INSTRUCTIONS("Отправляем запрос на получение инструкций"),
  SEND_REQUEST_UPLOAD("Отправляем запрос на верификацию"),
  SETTING_LOGGING("Подключаем конфигурацию логирования"),
  LOGGING_ENABLED("Логирование подключено"),
  LOGGING_PATH_INVALID("Переданный путь до конфигурации logger-а \"{}\" невалиден"),
  FAILED_ARGS(
      "Ошибка при разборе аргументов командной строки: аргументы = \"{}\", \" +\n\"сообщение об ошибке = \"{}\"");

  public final String message;

  Messages(String message) {
    this.message = message;
  }
}
