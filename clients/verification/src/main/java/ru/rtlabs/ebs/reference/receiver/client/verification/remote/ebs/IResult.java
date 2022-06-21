package ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import ru.rtlabs.ebs.reference.receiver.client.verification.exceptions.EbsResultException;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;

/**
 * Выполнение 4‑го запроса в сервис верификации(pdp). Получение расширенного результата
 * верификации.
 */
public interface IResult {
  /**
   * запрос на получение расширенного результата верификации.
   *
   * @param jwt       токен доступа, для получения расширенного результата верификации.
   * @param sessionId идентификатор сессии.
   * @throws IOException        ошибка при выполнении запроса.
   * @throws EbsResultException ошибка при выполнении запроса на получение расширенного результата
   *                            верификации.
   */
  void result(String jwt, String sessionId) throws IOException, EbsResultException;

  /**
   * Получение токена доступа для получения расширенного результата верификации.
   *
   * @return данные токена доступа.
   * @throws CryptoSignatureException исключение, связанные с сервисом подписания.
   * @throws JsonProcessingException  исключение, возникающие при генерации JSON файла.
   */
  String getResultJwt(String verifyToken)
      throws CryptoSignatureException, JsonProcessingException;
}
