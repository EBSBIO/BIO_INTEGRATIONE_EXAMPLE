package ru.rtlabs.ebs.reference.receiver.client.verification.exceptions;

/**
 * Ошибка при выполнении загрузки БО на верификацию.
 */
public class EbsUploadException extends EbsRequestException {
  public EbsUploadException(String message) {
    super(message);
  }
}
