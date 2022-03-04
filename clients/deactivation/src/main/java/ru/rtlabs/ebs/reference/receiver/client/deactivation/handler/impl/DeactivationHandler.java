package ru.rtlabs.ebs.reference.receiver.client.deactivation.handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.client.deactivation.config.DeactivationConfig;
import ru.rtlabs.ebs.reference.receiver.client.deactivation.handler.IDeactivationHandler;
import ru.rtlabs.ebs.reference.receiver.client.deactivation.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.client.deactivation.service.IDeactivationService;
import ru.rtlabs.ebs.reference.receiver.client.deactivation.service.impl.DeactivationService;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;

import java.io.IOException;

public final class DeactivationHandler implements IDeactivationHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(DeactivationHandler.class);
  private final DeactivationConfig config;

  public DeactivationHandler(
      DeactivationConfig config) {
    this.config = config;
  }

  /**
   * Запуск эталонного запроса на деактивацию УЗ.
   *
   * @throws IOException Исключение ввода/вывода.
   */
  @Override
  public void startProcess() throws IOException {
    LOGGER.info(Messages.LOGGING_INIT_CRYPTO.message);
    try {
      Response response;
      IDeactivationService deactivationService = new DeactivationService();
      String jwt = deactivationService.getDeactivationJwt(config);

      LOGGER.info(Messages.LOGGING_JWT_DEACTIVATION.message, jwt);
      response = deactivationService.sendSuccessRequest(config.base(), jwt);
      LOGGER.info(Messages.LOGGING_RESPONSE_DEACTIVATION.message, response.toString());
    } catch (CryptoSignatureException | JsonProcessingException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
