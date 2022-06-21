package ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions;


/**
 * Исключения, связанные с сервисом подписания.
 */
public class CryptoSignatureException extends CryptoException {
  /**
   * Конструктор.
   *
   * @param sigType  тип исключения (из представленного набора), должен содержать элементы для
   *                 форматирования строки
   * @param toFormat элементы, которыми нужно дополнить строку в sigType
   */
  public CryptoSignatureException(CryptoSignatureExceptionEnum sigType, String... toFormat) {
    super(sigType.message.formatted(toFormat));
  }

  /**
   * Конструктор.
   *
   * @param sigType  тип исключения (из представленного набора), должен содержать элементы для
   *                 форматирования строки
   * @param original исходное исключение
   * @param toFormat элементы, которыми нужно дополнить строку в sigType
   */
  public CryptoSignatureException(CryptoSignatureExceptionEnum sigType, Throwable original,
                                  String... toFormat) {
    super(sigType.message.formatted(toFormat), original);
  }
}
