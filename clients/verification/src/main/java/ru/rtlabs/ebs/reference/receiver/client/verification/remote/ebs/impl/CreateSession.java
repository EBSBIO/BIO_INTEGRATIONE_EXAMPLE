package ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.client.verification.config.VerificationConfig;
import ru.rtlabs.ebs.reference.receiver.client.verification.exceptions.EbsCreateSessionException;
import ru.rtlabs.ebs.reference.receiver.client.verification.logging.ErrorMessages;
import ru.rtlabs.ebs.reference.receiver.client.verification.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs.ICreateSession;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;
import ru.rtlabs.ebs.reference.receiver.jwt.base.JwtBuilder;

public class CreateSession implements ICreateSession {
  public static final String APPLICATION_JSON = "application/json";
  public static final MediaType JSON = MediaType.get(
      "%s; charset=utf-8".formatted(APPLICATION_JSON));
  private static final String COOKIE = "Cookie";
  public static final String AUTHORIZATION = "Authorization";
  public static final String CONTENT_TYPE = "Content-Type";
  private static final String BEARER = "Bearer ";
  private static final String EBS_SESSION_FIELD = "ebs.session";
  private static final Logger LOGGER = LoggerFactory.getLogger(CreateSession.class);
  private final VerificationConfig config;
  private final OkHttpClient client;

  public CreateSession(VerificationConfig config, OkHttpClient client) {
    this.config = config;
    this.client = client;
  }

  @Override
  public void createSession(String jwt, String sessionId)
      throws IOException, EbsCreateSessionException {

    Request request = new Request.Builder()
        .header(COOKIE, "%s=%s".formatted(EBS_SESSION_FIELD, sessionId))
        .header(CONTENT_TYPE, APPLICATION_JSON)
        .header(AUTHORIZATION, BEARER + jwt)
        .url("%s%s".formatted(config.ebsConfig().host(), config.ebsConfig().createSessionUrl()))
        .post(RequestBody.Companion.create(config.ebsConfig().createSession().metadata(), JSON))
        .build();

    LOGGER.info(Messages.SEND_REQUEST_CREATE_SESSION.message);
    LOGGER.info(Messages.SEND_REQUEST.message, request);

    Response response = client.newCall(request).execute();

    int code = response.code();
    ResponseBody responseBody = response.body();
    String body = responseBody == null ? "" : responseBody.string();

    LOGGER.info(Messages.REQUEST_RESULT.message, body, code);

    if (code != 200) {
      throw new EbsCreateSessionException(
          ErrorMessages.CREATE_SESSION_FAILED.message.formatted(body, code));
    }
  }


  @Override
  public String getCreateSessionJwt() throws CryptoSignatureException, JsonProcessingException {
    return (config.isGenerateJwt()) ? getCreateSessionJwtFromCryptoPro()
                                    : config.ebsConfig().createSession().jwt();
  }


  /**
   * Получение токена доступа для создания сессии в EBS, сформированного в Крипто-Про.
   *
   * @return данные токена доступа.
   * @throws CryptoSignatureException исключения, связанные с сервисом подписания.
   * @throws JsonProcessingException  исключения, возникающие при генерации JSON файла.
   */
  private String getCreateSessionJwtFromCryptoPro()
      throws CryptoSignatureException, JsonProcessingException {
    JwtBuilder builder = new JwtBuilder(
        config.ebsConfig().createSession().jwtData().payload(),
        config.ebsConfig().createSession().jwtData().header());
    return builder.build(config.cryptoConfig());
  }
}
