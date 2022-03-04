package ru.rtlabs.ebs.reference.receiver.client.deactivation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import okhttp3.Response;
import ru.rtlabs.ebs.reference.receiver.client.deactivation.config.BaseConfig;
import ru.rtlabs.ebs.reference.receiver.client.deactivation.config.DeactivationConfig;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;

public interface IDeactivationService {

  /**
   * Отправка эталонного запроса на деактивацию УЗ.
   *
   * @param config      Данные основных настроек деактивации.
   * @param accessToken Токен доступа JWT.
   * @return Данные ответа от сервиса деактивации УЗ.
   * @throws IOException Исключение ввода/вывода.
   */
  Response sendSuccessRequest(BaseConfig config, String accessToken) throws IOException;

  /**
   * Получение токена доступа.
   *
   * @param config Данные конфигурации деактивации.
   * @return данные токена доступа.
   * @throws CryptoSignatureException исключения, связанные с сервисом подписания.
   * @throws JsonProcessingException  исключения, возникающие при генерации JSON файла.
   */
  String getDeactivationJwt(DeactivationConfig config) throws CryptoSignatureException, JsonProcessingException;
}
