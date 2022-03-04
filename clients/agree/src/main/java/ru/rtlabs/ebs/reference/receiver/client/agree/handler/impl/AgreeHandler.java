package ru.rtlabs.ebs.reference.receiver.client.agree.handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.net.MalformedURLException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.client.agree.config.AgreeConfig;
import ru.rtlabs.ebs.reference.receiver.client.agree.handler.IAgreeHandler;
import ru.rtlabs.ebs.reference.receiver.client.agree.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.client.agree.service.AgreeService;
import ru.rtlabs.ebs.reference.receiver.client.agree.service.IAgreeService;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;
import ru.rtlabs.ebs.reference.receiver.jwt.base.Jwt;
import ru.rtlabs.ebs.reference.receiver.jwt.base.JwtBuilder;

public class AgreeHandler implements IAgreeHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(AgreeHandler.class);
  private final IAgreeService agreeService;

  AgreeConfig agreeConfig;

  public AgreeHandler(AgreeConfig agreeConfig) {
    this.agreeConfig = agreeConfig;
    this.agreeService = new AgreeService();
  }

  public void startProcess() throws CryptoSignatureException, JsonProcessingException {
    String jwt;
    if (agreeConfig.generalData().needGenerateToken()) {
      LOGGER.info(Messages.START_CREATE_JWT_FROM_CONFIG.message);
      Jwt.Header header = agreeConfig.headersData();
      Jwt.Payload payload = agreeConfig.payloadData();
      LOGGER.info(Messages.INIT_LOCAL_CRYPTO.message);
      LOGGER.info(Messages.HEADERS_AND_PAYLOAD_FOR_JWT.message, header, payload);
      JwtBuilder builder = new JwtBuilder(payload, header);
      jwt = builder.build(agreeConfig.cryptoConfig());
      LOGGER.info(Messages.JWT_RESULT.message, jwt);

    } else {
      LOGGER.info(Messages.EXISTING_JWT_FROM_CONFIG.message);
      jwt = (agreeConfig.jwtObject().jwt());
    }

    URL url = getAgreeServerUrl(agreeConfig.generalData().serviceUrl(),
                                agreeConfig.generalData().registrationPath());
    agreeService.sendRegistration(url, jwt);

  }

  /**
   * Формирование полного пути для отправки запроса.
   *
   * @param url  адрес, куда отправлять запрос.
   * @param path endpoint.
   * @return полный URL.
   */
  private URL getAgreeServerUrl(String url, String path) {
    try {
      return new URL(url + path);
    } catch (MalformedURLException e) {
      LOGGER.error(Messages.URL_ERROR.message, e.getMessage(), e);
      return null;
    }
  }
}
