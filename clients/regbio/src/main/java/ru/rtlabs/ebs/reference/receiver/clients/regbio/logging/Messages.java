package ru.rtlabs.ebs.reference.receiver.clients.regbio.logging;

public enum Messages {
  SETTING_LOGGING("Подключаем конфигурацию логирования"),
  LOGGING_ENABLED("Логирование подключено"),
  LOGGING_PATH_INVALID("Переданный путь до конфигурации logger-а '{}' невалиден"),
  FAILED_ARGS("Ошибка при разборе аргументов командной строки: аргументы = \"{}\", \" +\n"
              + "          \"сообщение об ошибке = \"{}\"");

  public final String message;

  Messages(String message) {
    this.message = message;
  }
}
