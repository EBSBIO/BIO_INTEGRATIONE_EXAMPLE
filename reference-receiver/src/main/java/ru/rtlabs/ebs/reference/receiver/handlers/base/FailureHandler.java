package ru.rtlabs.ebs.reference.receiver.handlers.base;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.exception.BadRequestException;
import ru.rtlabs.ebs.reference.receiver.exception.ErrorApiCodesEnum;
import ru.rtlabs.ebs.reference.receiver.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.marshalling.ErrorResponseData;

/**
 * Обработка ошибок и формирование ответа.
 */
public class FailureHandler implements Handler<RoutingContext> {
  private static final String JSON_HEADER = "Content-Type";
  private static final String JSON_CONTENT_TYPE = "application/json";

  private static final Logger LOGGER = LoggerFactory.getLogger(FailureHandler.class);

  @Override
  public void handle(RoutingContext routingContext) {
    Throwable throwable = routingContext.failure();
    LOGGER.error(throwable.getMessage(), throwable);
    if (throwable instanceof BadRequestException badRequestException) {
      createResponse(routingContext, badRequestException.getError(),
                     throwable.getMessage());
    } else {
      createResponse(routingContext, ErrorApiCodesEnum.INTERNAL_SERVER_ERROR,
                     throwable.getMessage());
    }
  }

  private void createResponse(RoutingContext routingContext, ErrorApiCodesEnum error,
                              String message) {

    String response = Json.encode(new ErrorResponseData(error.getHttpCode(),
                                                        error.getMessage().formatted(message)));

    LOGGER.info(Messages.SEND_FAILED_RESPONSE.message, response);

    routingContext.response()
                  .setStatusCode(error.getHttpCode())
                  .putHeader(JSON_HEADER, JSON_CONTENT_TYPE)
                  .end(response);
  }
}
