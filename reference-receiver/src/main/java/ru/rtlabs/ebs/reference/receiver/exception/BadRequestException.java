package ru.rtlabs.ebs.reference.receiver.exception;


/**
 * Ошибка возникает при некорректном запросе.
 */
public class BadRequestException extends RuntimeException {

  private final ErrorApiCodesEnum error;

  public BadRequestException(ErrorApiCodesEnum error) {
    this.error = error;
  }

  public BadRequestException(ErrorApiCodesEnum error, String message) {
    super(message);
    this.error = error;
  }

  public ErrorApiCodesEnum getError() {
    return error;
  }
}
