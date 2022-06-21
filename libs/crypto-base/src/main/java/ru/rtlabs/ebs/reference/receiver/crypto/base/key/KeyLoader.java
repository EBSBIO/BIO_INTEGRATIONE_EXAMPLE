package ru.rtlabs.ebs.reference.receiver.crypto.base.key;

import java.security.PrivateKey;
import java.security.cert.Certificate;
import ru.rtlabs.ebs.reference.receiver.base.Pair;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;

/**
 * Класс отвечает за получение закрытого ключа и сертификата.
 */
public interface KeyLoader {
  /**
   * Метод для получения закрытого ключа криптопровайдером.
   *
   * @param keyId    идентификатор ключа.
   * @param password пароль для получения доступа к контейнеру.
   * @return идентификатор алгоритма и закрытый ключ.
   * @throws CryptoSignatureException исключения, возникающие при неправильной информации для
   *                                  получения ключа, его отсутствии и иных ошибок
   *                                  криптопровайдера.
   */
  Pair<String, PrivateKey> getPrivateKey(String keyId, String password)
      throws CryptoSignatureException;

  /**
   * Метод получения сертификата криптопровайдером.
   *
   * @param keyId идентификатор контейнера, который держит сертификат - открытый ключ.
   * @return идентификатор алгоритма и сертификат.
   * @throws CryptoSignatureException исключения, возникающие при неправильной информации для
   *                                  получения ключа, его отсутствии и иных ошибок
   *                                  криптопровайдера.
   */
  Pair<String, Certificate> getCertificate(String keyId) throws CryptoSignatureException;
}
