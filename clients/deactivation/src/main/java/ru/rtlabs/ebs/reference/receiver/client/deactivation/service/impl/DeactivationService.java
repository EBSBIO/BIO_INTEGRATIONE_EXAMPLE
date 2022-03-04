package ru.rtlabs.ebs.reference.receiver.client.deactivation.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.client.deactivation.config.BaseConfig;
import ru.rtlabs.ebs.reference.receiver.client.deactivation.config.DeactivationConfig;
import ru.rtlabs.ebs.reference.receiver.client.deactivation.jwt.Header;
import ru.rtlabs.ebs.reference.receiver.client.deactivation.jwt.Payload;
import ru.rtlabs.ebs.reference.receiver.client.deactivation.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.client.deactivation.service.IDeactivationService;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;
import ru.rtlabs.ebs.reference.receiver.jwt.base.JwtBuilder;

public class DeactivationService implements IDeactivationService {
  public static final String AUTHORIZATION = "Authorization";
  private static final String BEARER = "Bearer ";
  public static final String TRACE_PROCESS_HEADER_NAME = "X-EBS-TraceProcess";
  public static final String V3 = "v3";
  private static final String DEACTIVATION_ACCOUNT_V3_NOT_ESIA_PATH = "users/deactivate-acc";
  private static final Logger LOGGER = LoggerFactory.getLogger(DeactivationService.class);
  private final OkHttpClient client;

  public DeactivationService() {
    this.client = new OkHttpClient().newBuilder()
        .readTimeout(100, TimeUnit.SECONDS)
        .followRedirects(false)
        .followSslRedirects(false)
        .build();
  }

  /**
   * Отправка эталонного запроса на деактивацию УЗ.
   *
   * @param config      Данные основных настроек деактивации.
   * @param accessToken Токен доступа JWT.
   * @return Данные ответа от сервиса деактивации УЗ.
   * @throws IOException Исключение ввода/вывода.
   */
  @Override
  public Response sendSuccessRequest(BaseConfig config, String accessToken) throws IOException {
    Map<String, String> headers = new HashMap<>();
    Optional.ofNullable(accessToken).ifPresent(value -> headers.put(AUTHORIZATION, BEARER + accessToken));
    headers.put(TRACE_PROCESS_HEADER_NAME, Boolean.toString(true));
    headers.put("Cookie", "ebs.session=\"{}\"".formatted(config.requestId()));
    Request request = new Request.Builder()
        .url(getUrl(config.host()))
        .headers(Headers.of(headers))
        .delete()
        .build();
    LOGGER.info(Messages.LOGGING_SEND_DEACTIVATION.message);
    return client.newCall(request).execute();
  }

  /**
   * Получение токена доступа.
   *
   * @param config Данные конфигурации деактивации.
   * @return данные токена доступа.
   * @throws CryptoSignatureException исключения, связанные с сервисом подписания.
   * @throws JsonProcessingException  исключения, возникающие при генерации JSON файла.
   */
  @Override
  public String getDeactivationJwt(DeactivationConfig config) throws CryptoSignatureException, JsonProcessingException {
    return (config.base().isJwtFromConfig()) ? config.createSessionJwt().jwt() : getDeactivationJwtFromCryptoPro(config);
  }

  /**
   * Получение токена доступа, сформированного в Крипто-Про.
   *
   * @param config Данные конфигурации деактивации.
   * @return данные токена доступа.
   * @throws CryptoSignatureException исключения, связанные с сервисом подписания.
   * @throws JsonProcessingException  исключения, возникающие при генерации JSON файла.
   */
  private String getDeactivationJwtFromCryptoPro(DeactivationConfig config) throws CryptoSignatureException, JsonProcessingException {
    JwtBuilder builder = new JwtBuilder(
        new Payload(
            config.createSessionJwtData().payload().sub(),
            config.createSessionJwtData().payload().iss(),
            config.createSessionJwtData().payload().certId(),
            config.createSessionJwtData().payload().exp(),
            config.createSessionJwtData().payload().iat(),
            config.createSessionJwtData().payload().nbf()),
        new Header(
            config.createSessionJwtData().header().ver(),
            config.createSessionJwtData().header().typ(),
            config.createSessionJwtData().header().sbt(),
            config.createSessionJwtData().header().alg()));
    return builder.build(config.cryptoConfig());
  }

  /**
   * Получение url'а деактивации УЗ.
   *
   * @param host Адрес сервиса деактивации УЗ.
   * @return Url деактивации УЗ.
   */
  private String getUrl(String host) {
    return String.format("%s/api/%s/%s", host, DeactivationService.V3, DeactivationService.DEACTIVATION_ACCOUNT_V3_NOT_ESIA_PATH);
  }
}
