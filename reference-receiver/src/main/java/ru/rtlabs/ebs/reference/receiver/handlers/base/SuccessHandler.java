package ru.rtlabs.ebs.reference.receiver.handlers.base;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.service.utils.Status;

/**
 * Формирование положительного ответа.
 */
public class SuccessHandler implements Handler<RoutingContext> {
  private static final Logger LOGGER = LoggerFactory.getLogger(SuccessHandler.class);

  @Override
  public void handle(RoutingContext routingContext) {
    LOGGER.info(Messages.SUCCESS_RESPONSE.message);
    routingContext.response()
                  .setStatusCode(Status.OK.getCode())
                  .end();
  }
}
