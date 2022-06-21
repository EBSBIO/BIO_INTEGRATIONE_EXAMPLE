package ru.rtlabs.ebs.reference.receiver.client.verification.exceptions;

/**
 * Ошибка при выполнении запроса на создание сессии в ЕБС.
 */
public class EbsCreateSessionException extends EbsRequestException {
  public EbsCreateSessionException(String message) {
    super(message);
  }
}
