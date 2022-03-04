package ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs;

import java.io.IOException;
import ru.rtlabs.ebs.reference.receiver.client.verification.exceptions.EbsUploadException;

/**
 * Выполнение 3‑го запроса в сервис верификации(pdp). Отправка биометрических данных для прохождения
 * верификации.
 */
public interface IUploadBiometricData {
  /**
   * Метод для выполнения верификации пользователя.
   *
   * @param sessionId идентификатор сессии, с которым будет выполнен запрос.
   * @return возвращает верификационный токен.
   * @throws IOException        ошибка при чтении файла.
   * @throws EbsUploadException ошибка при выполнении запроса на верификацию.
   */
  String uploadBioData(String sessionId) throws IOException, EbsUploadException;
}
