package ru.rtlabs.ebs.reference.receiver.crypto.base.functions;

import java.security.PrivateKey;
import java.security.cert.Certificate;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoException;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;

/**
 * Механизмы подписания и проверки подписи.
 */
public interface SignatureFunction {
  /**
   * Подпись данных.
   *
   * @param origin      оригинальные данные для подписи.
   * @param privateKey  закрытый ключ.
   * @param certificate сертификат (будет добавлен в CMS-структуры).
   * @return подпись.
   * @throws CryptoSignatureException в случае ошибок подписания.
   */
  byte[] sign(byte[] origin, PrivateKey privateKey, Certificate certificate)
      throws CryptoSignatureException;

  /**
   * Проверка подписи данных.
   *
   * @param origin      оригинальные данные, по которым была получена подпись.
   * @param sign        подпись для проверки.
   * @param certificate сертификат для проверки подписи.
   * @return true, если подпись валидна, иначе false.
   * @throws CryptoException в случае ошибок при проверке подписи и в случае ошибок при проверке
   *                         сертификата.
   */
  boolean verify(byte[] origin, byte[] sign, Certificate certificate) throws CryptoException;

  /**
   * Шифрование БО.
   *
   * @param data  данные которые будут зашифрованы
   * @param certificate  сертификат, который будет использоваться при шифровании
   * @return массив зашифроманных байтов
   * @throws CryptoException в случае ошибок при проверке подписи и в случае ошибок при проверке
   *                         сертификата.
   */
  byte[] encrypt(byte[] data, Certificate certificate) throws CryptoException;
}
