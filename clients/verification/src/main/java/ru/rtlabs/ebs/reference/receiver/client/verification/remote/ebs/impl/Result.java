package ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.client.verification.config.VerificationConfig;
import ru.rtlabs.ebs.reference.receiver.client.verification.config.ebs.jwt.result.PayloadResult;
import ru.rtlabs.ebs.reference.receiver.client.verification.exceptions.EbsResultException;
import ru.rtlabs.ebs.reference.receiver.client.verification.logging.ErrorMessages;
import ru.rtlabs.ebs.reference.receiver.client.verification.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs.IResult;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;
import ru.rtlabs.ebs.reference.receiver.jwt.base.JwtBuilder;


public class Result implements IResult {
  public static final String APPLICATION_JSON = "application/json";
  public static final MediaType JSON = MediaType.get(
      "%s; charset=utf-8".formatted(APPLICATION_JSON));
  private static final String COOKIE = "Cookie";
  public static final String AUTHORIZATION = "Authorization";
  public static final String EXTENDED_RESULT = "extended_result";
  public static final String CONTENT_TYPE = "Content-Type";
  private static final String BEARER = "Bearer ";
  private static final String EBS_SESSION_FIELD = "ebs.session";
  private static final Logger LOGGER = LoggerFactory.getLogger(Result.class);
  private final VerificationConfig config;
  private final OkHttpClient client;

  public Result(VerificationConfig config, OkHttpClient client) {
    this.config = config;
    this.client = client;
  }

  @Override
  public void result(String jwt, String sessionId)
      throws IOException, EbsResultException {

    Request request = new Request.Builder()
        .header(COOKIE, "%s=%s".formatted(EBS_SESSION_FIELD, sessionId))
        .header(CONTENT_TYPE, APPLICATION_JSON)
        .header(AUTHORIZATION, BEARER + jwt)
        .url("%s%s".formatted(config.ebsConfig().host(),
                              config.ebsConfig().resultUrl().formatted(sessionId)))
        .get()
        .build();

    LOGGER.info(Messages.SEND_REQUEST_RESULT.message);
    LOGGER.info(Messages.SEND_REQUEST.message, request);

    Response response = client.newCall(request).execute();

    int code = response.code();
    ResponseBody responseBody = response.body();
    String body = responseBody == null ? "" : responseBody.string();

    LOGGER.info(Messages.REQUEST_RESULT.message, body, code);

    if (code != 200) {
      throw new EbsResultException(
          ErrorMessages.GET_RESULT_FAILED.message.formatted(body, code));
    }

    String extendedResult = response.header(EXTENDED_RESULT);

    if (extendedResult == null || extendedResult.isBlank()) {
      throw new EbsResultException(ErrorMessages.EXTENDED_RESULT_GET_FAILED.message);
    }
    LOGGER.info(Messages.EXTENDED_RESULT_GET_SUCCESS.message, extendedResult);

  }


  @Override
  public String getResultJwt(String verifyToken)
      throws CryptoSignatureException, JsonProcessingException {
    return (config.isGenerateJwt()) ? getResultJwtFromCryptoPro(verifyToken)
                                    : config.ebsConfig().createSession().jwt();
  }


  /**
   * Получение токена доступа для расширенного результата в EBS, сформированного в Крипто-Про.
   * Данный метод берет данные из конфигов и добавляет verify_token в результат.
   *
   * @return данные токена доступа.
   * @throws CryptoSignatureException исключения, связанные с сервисом подписания.
   * @throws JsonProcessingException  исключения, возникающие при генерации JSON файла.
   */
  private String getResultJwtFromCryptoPro(String verifyToken)
      throws CryptoSignatureException, JsonProcessingException {
    PayloadResult payload = config.ebsConfig().result().jwtData().payload();
    // NOTICE: пересобираем payload вместе с verify_token. Так как мы заранее его не знаем, то не можем получить его из конфигов.
    // Данный токен был получен на предыдущем этапе
    payload = new PayloadResult(payload.iat(),
                                payload.sub(),
                                payload.aud(),
                                payload.exp(),
                                payload.nbf(),
                                payload.iss(),
                                payload.clientId(),
                                verifyToken);
    JwtBuilder builder = new JwtBuilder(
        payload,
        config.ebsConfig().result().jwtData().header());
    return builder.build(config.cryptoConfig());
  }
}
