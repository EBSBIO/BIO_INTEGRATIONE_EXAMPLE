package ru.rtlabs.ebs.reference.receiver.client.verification.exceptions;

/**
 * Базовая ошибка при выполнении запроса в ЕБС.
 */
public class EbsRequestException extends Exception {
  public EbsRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public EbsRequestException(String message) {
    super(message);
  }
}
