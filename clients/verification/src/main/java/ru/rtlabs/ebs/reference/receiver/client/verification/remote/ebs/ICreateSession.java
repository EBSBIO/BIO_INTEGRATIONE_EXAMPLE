package ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs;


import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import ru.rtlabs.ebs.reference.receiver.client.verification.exceptions.EbsCreateSessionException;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;

/**
 * Выполнение 1‑го запроса в сервис верификации(pdp). Создание сессии для верификации.
 */
public interface ICreateSession {
  /**
   * Запрос на создание сессии верификации в ЕБС.
   *
   * @param jwt       токен доступа для создания сессии.
   * @param sessionId сгенерированный идентификатор сессии.
   * @throws IOException               ошибка при выполнении запроса.
   * @throws EbsCreateSessionException ошибка при выполнении запроса на создание сессии.
   */
  void createSession(String jwt, String sessionId) throws IOException, EbsCreateSessionException;

  /**
   * Получение токена доступа для создания сессии в ЕБС.
   *
   * @return данные токена доступа.
   * @throws CryptoSignatureException исключения, связанные с сервисом подписания.
   * @throws JsonProcessingException  исключения, возникающие при генерации JSON файла.
   */
  String getCreateSessionJwt() throws CryptoSignatureException, JsonProcessingException;
}
