package ru.rtlabs.ebs.reference.receiver.client.agree.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;

public interface IAgreeHandler {
  /**
   * Хэндлер для запуска процесса отправки запроса на agree.
   *
   * @throws CryptoSignatureException ошибка подписания JWT
   * @throws JsonProcessingException  ошибка формирования JWT
   */
  void startProcess() throws CryptoSignatureException, JsonProcessingException;
}
