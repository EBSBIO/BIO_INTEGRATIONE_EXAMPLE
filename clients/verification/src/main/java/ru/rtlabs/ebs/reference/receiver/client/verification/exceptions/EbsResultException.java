package ru.rtlabs.ebs.reference.receiver.client.verification.exceptions;

/**
 * Ошибка при выполнении запроса на получение расширенного результата верификации.
 */
public class EbsResultException extends EbsRequestException {
  public EbsResultException(String message) {
    super(message);
  }
}
