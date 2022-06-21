package ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs;

import java.io.IOException;
import ru.rtlabs.ebs.reference.receiver.client.verification.exceptions.EbsGetInstructionsException;

/**
 * Выполнение 2‑го запроса в сервис верификации(pdp). Данный запрос является *обязательным* только в
 * случае наличия активного liveness в мнемонике. В остальных случаях запрос *не* нужен. Выполняет
 * запрос на получение инструкций для прохождения верификации с активным liveness.
 */
public interface IGetInstructions {
  /**
   * Выполнение запроса на получение инструкций. Запрос должен выполняться, только если мнемоника
   * поддерживает liveness.
   *
   * @param sessionId идентификатор сессии, для которого создана верификационная сессия.
   * @throws IOException                 ошибка при выполнении запроса.
   * @throws EbsGetInstructionsException ошибка при выполнении запроса на получение инструкций
   *                                     сессии.
   */
  void getInstructions(String sessionId) throws IOException, EbsGetInstructionsException;
}
