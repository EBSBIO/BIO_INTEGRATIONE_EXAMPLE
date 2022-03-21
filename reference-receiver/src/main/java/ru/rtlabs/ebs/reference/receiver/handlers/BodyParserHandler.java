package ru.rtlabs.ebs.reference.receiver.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.exception.BadRequestException;
import ru.rtlabs.ebs.reference.receiver.exception.ErrorApiCodesEnum;
import ru.rtlabs.ebs.reference.receiver.logging.Messages;

/**
 * Хэндлер для парсинга тела запроса от idp-notifier, matching-notifier.
 */
public class BodyParserHandler<T> implements Handler<RoutingContext> {
  private static final Logger LOGGER = LoggerFactory.getLogger(BodyParserHandler.class);
  private final Class<T> parserClass;

  public BodyParserHandler(Class<T> parserClass) {
    this.parserClass = parserClass;
  }

  @Override
  public void handle(RoutingContext routingContext) {
    LOGGER.info(Messages.PARSING_BODY.message);
    try {
      String body = routingContext.getBodyAsString();
      if (body == null || body.isBlank()) {
        LOGGER.error(Messages.ERROR_EMPTY_BODY.message);
        routingContext.fail(new BadRequestException(ErrorApiCodesEnum.BAD_REQUEST,
                                                    Messages.WRONG_INPUT.message));
      } else {
        T decodeValue = Json.decodeValue(body, parserClass);
        LOGGER.info(Messages.GET_REQUEST.message, decodeValue);
        routingContext.next();
      }
    } catch (DecodeException e) {
      LOGGER.error(Messages.ERROR_PARSE_REQUEST.message);
      LOGGER.error(e.getMessage(), e);
      routingContext
          .fail(new BadRequestException(ErrorApiCodesEnum.BAD_REQUEST,
                                        Messages.WRONG_INPUT.message));
    }
  }
}
