package ru.rtlabs.ebs.reference.receiver.handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import ru.rtlabs.ebs.reference.receiver.service.utils.ContextData;

/**
 * Сохранение JWT из запроса в контекст. Используется при получении запроса от bioskud-router при
 * деактивации.
 */
public class SaveJWTHandler implements Handler<RoutingContext> {

  @Override
  public void handle(RoutingContext routingContext) {
    String jwtString = routingContext.getBodyAsString();
    routingContext.put(ContextData.JWT_TOKEN.key, jwtString);
    routingContext.next();
  }
}
