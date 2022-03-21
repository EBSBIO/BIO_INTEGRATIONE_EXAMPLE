package ru.rtlabs.ebs.reference.receiver.handlers.base;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;
import ru.rtlabs.ebs.reference.receiver.exception.BadRequestException;
import ru.rtlabs.ebs.reference.receiver.exception.ErrorApiCodesEnum;
import ru.rtlabs.ebs.reference.receiver.logging.Messages;

/**
 * Проверка авторизации. Обработчик проверяет, что в заголовках лежат авторизационные данные.
 */
public class CheckAuthHeaderHandler implements Handler<RoutingContext> {
  private final String authBearer;

  public CheckAuthHeaderHandler(String authBearer) {
    this.authBearer = authBearer;
  }

  @Override
  public void handle(RoutingContext routingContext) {

    String accessToken = routingContext.request().getHeader(HttpHeaders.AUTHORIZATION);
    if (accessToken == null || !accessToken.equals("Bearer %s".formatted(authBearer))) {
      routingContext.fail(new BadRequestException(ErrorApiCodesEnum.UNAUTHORIZED,
                                                  Messages.UNAUTHORIZED.message));
    } else {
      routingContext.next();

    }
  }
}
