package ru.rtlabs.ebs.reference.receiver.client.verification.exceptions;

/**
 * Ошибка при выполнении запроса на получение инструкций.
 */
public class EbsGetInstructionsException extends EbsRequestException {
  public EbsGetInstructionsException(String message) {
    super(message);
  }
}
