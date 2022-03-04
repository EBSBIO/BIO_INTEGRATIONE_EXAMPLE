package ru.rtlabs.ebs.reference.receiver.client.deactivation.logging;

public enum Messages {
  SETTING_LOGGING("Подключаем конфигурацию логирования"),
  LOGGING_ENABLED("Логирование подключено"),
  LOGGING_JWT_DEACTIVATION("Данные jwt токена: \"{}\""),
  LOGGING_RESPONSE_DEACTIVATION("Ответ от сервиса деактивации УЗ: \"{}\""),
  LOGGING_SEND_DEACTIVATION("DEACTIVATION: отправляем запрос на деактивацию пользователя."),
  LOGGING_INIT_CRYPTO("Инициализируем локальную работу с криптопровайдером"),
  LOGGING_PATH_INVALID("Переданный путь до конфигурации logger-а '{}' невалиден"),
  FAILED_ARGS("Ошибка при разборе аргументов командной строки: аргументы = \"{}\", \" +\n"
              + "          \"сообщение об ошибке = \"{}\"");

  public final String message;

  Messages(String message) {
    this.message = message;
  }
}
