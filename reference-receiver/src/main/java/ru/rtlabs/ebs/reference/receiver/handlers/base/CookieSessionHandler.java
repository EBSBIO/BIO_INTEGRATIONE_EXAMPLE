package ru.rtlabs.ebs.reference.receiver.handlers.base;

import io.vertx.core.Handler;
import io.vertx.core.http.Cookie;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.exception.BadRequestException;
import ru.rtlabs.ebs.reference.receiver.exception.ErrorApiCodesEnum;
import ru.rtlabs.ebs.reference.receiver.logging.Messages;

/**
 * Обработчик проверки и получения информации ebs.session из cookie.
 */
public class CookieSessionHandler implements Handler<RoutingContext> {
  private static final Logger LOGGER = LoggerFactory.getLogger(CookieSessionHandler.class);
  private static final String EBS_SESSION_FIELD = "ebs.session";


  public void handle(RoutingContext routingContext) {
    Cookie cookie = routingContext.request().getCookie(EBS_SESSION_FIELD);
    if (cookie == null) {
      routingContext.fail(new BadRequestException(ErrorApiCodesEnum.BAD_REQUEST,
                                                  Messages.ERROR_COOKIE_EBS_SESSION_NOT_FOUND.message));
    } else {
      LOGGER.info(Messages.CREATE_TRANSACTION_ID.message);
      routingContext.next();
    }
  }
}
