package ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions;

/**
 * Список ошибок для механизма исключений сервиса Адаптера.
 */
public enum CryptoSignatureExceptionEnum {
  WRONG_CRYPTOPROVIDER("Предоставлено неверное имя криптопровайдера: %s"),
  WRONG_ALGORITHM_NAME("Алгоритма с таким именем не существует: %s"),
  WRONG_KEY("Передан неверный закрытый ключ"),
  ERROR_SIGNING_INFO("Ошибка во время подписания объекта"),
  ERROR_OPENING_KEYSTORE("Ошибка открытия хранилища ключей %s"),
  MISSING_KEY_IN_STORE("Отсутствует ключ/сертификат %s в хранилище контейнеров"),
  UNKNOWN_ERROR_PROVIDER("Неизвестная ошибка криптопровайдера %s во время %s");

  /**
   * Текст исключения (возможен как форматная строка, так и как обычный текст.
   */
  public final String message;

  CryptoSignatureExceptionEnum(String message) {
    this.message = message;
  }
}
