package ru.rtlabs.ebs.reference.receiver.exception;

/**
 * Внутренняя ошибка сервиса.
 */
public class ServerServiceException extends Exception {
  public ServerServiceException(String s, Throwable throwable) {
    super(s, throwable);
  }
}
