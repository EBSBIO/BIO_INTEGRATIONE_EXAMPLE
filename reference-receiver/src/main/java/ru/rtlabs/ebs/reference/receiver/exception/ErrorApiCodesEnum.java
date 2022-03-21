package ru.rtlabs.ebs.reference.receiver.exception;

public enum ErrorApiCodesEnum {

  NOT_FOUND_REQUIRED_DATA("Отсутствуют обязательные данные: %s", 400),
  BAD_REQUEST("Неверный запрос", 400),
  UNAUTHORIZED("Отказано в авторизации", 401),
  INTERNAL_SERVER_ERROR("Внутренняя ошибка API", 500);

  private final String message;
  private final int httpCode;

  ErrorApiCodesEnum(String message, int httpCode) {
    this.message = message;
    this.httpCode = httpCode;
  }

  public String getMessage() {
    return message;
  }

  public int getHttpCode() {
    return httpCode;
  }
}
