package ru.rtlabs.ebs.reference.receiver.client.verification.logging;

public enum ErrorMessages {
  GET_INSTRUCTIONS_FAILED("Ошибка при получении инструкций: body \"%s\", code \"%s\""),
  CREATE_SESSION_FAILED("Ошибка создания сессии: body \"%s\", code \"%s\""),
  GET_RESULT_FAILED("Ошибка получения результата верификации: body \"%s\", code \"%s\""),
  VERIFICATION_FAILED("Ошибка при выполнении запроса на верификацию: body \"%s\", code \"%s\""),
  VERIFICATION_TOKEN_GET_FAILED("Верификационный токен не получен!"),
  EXTENDED_RESULT_GET_FAILED("Расширенный результат не получен!");

  public final String message;

  ErrorMessages(String message) {
    this.message = message;
  }
}
