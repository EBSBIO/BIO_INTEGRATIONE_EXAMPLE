package ru.rtlabs.ebs.reference.receiver.jwt.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.rtlabs.ebs.reference.receiver.configurations.crypto.CryptoConfig;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;

public interface Jwt {
  /**
   * Маркер для payload объектов.
   */
  interface Payload {
  }

  /**
   * Маркер для header объектов.
   */
  interface Header {
  }

  interface Builder {
    /**
     * Метод для выполнения формирования JWT с подписью.
     *
     * @param cryptoConfig данные для формирования подписи.
     * @return сформированную подпись в String формате.
     * @throws CryptoSignatureException ошибка при работе с сервисом подписания.
     * @throws JsonProcessingException  ошибка при парсинге данных.
     */
    String build(CryptoConfig cryptoConfig)
        throws CryptoSignatureException, JsonProcessingException;
  }

}
