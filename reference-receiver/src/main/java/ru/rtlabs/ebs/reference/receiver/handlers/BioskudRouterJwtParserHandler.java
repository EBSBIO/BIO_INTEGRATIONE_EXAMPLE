package ru.rtlabs.ebs.reference.receiver.handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.BaseHeader;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.BaseJwt;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.BasePayload;
import ru.rtlabs.ebs.reference.receiver.service.jwt.BaseValidator;
import ru.rtlabs.ebs.reference.receiver.service.utils.ContextData;

/**
 * Хэндлер для получения jwt из запроса и сохранения его в контекст. Используется при запросах на
 * выгрузку и деактивацию.
 *
 * @param <H> объект для header.
 * @param <P> объект для payload.
 */
public class BioskudRouterJwtParserHandler<H extends BaseHeader, P extends BasePayload>
    implements Handler<RoutingContext> {
  private static final Logger LOGGER = LoggerFactory.getLogger(
      BioskudRouterJwtParserHandler.class);
  private final BaseValidator<H, P> validator;

  public BioskudRouterJwtParserHandler(BaseValidator<H, P> validator) {
    this.validator = validator;
  }


  @Override
  public void handle(RoutingContext routingContext) {
    LOGGER.info(Messages.PARSING_BODY_BIOSKUD_ROUTER_REQUEST.message);
    String jwtString = routingContext.get(ContextData.JWT_TOKEN.key);
    LOGGER.info(Messages.GET_REQUEST.message, jwtString);
    // NOTICE тут надо добавить проверку jwt, в текущей реализации проверяется только обязательность полей.
    BaseJwt<H, P> jwt = validator.parserJwt(jwtString);
    LOGGER.info(Messages.GET_JWT.message, jwt);
    routingContext.put(ContextData.JWT_OBJECT.key, jwt);
    routingContext.next();
  }
}
