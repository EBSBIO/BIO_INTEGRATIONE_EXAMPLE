package ru.rtlabs.ebs.reference.receiver.handlers.base;

import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.RoutingContext;
import java.util.List;
import java.util.Optional;
import ru.rtlabs.ebs.reference.receiver.exception.BadRequestException;
import ru.rtlabs.ebs.reference.receiver.exception.ErrorApiCodesEnum;
import ru.rtlabs.ebs.reference.receiver.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.marshalling.MultipartData;
import ru.rtlabs.ebs.reference.receiver.service.utils.ContextData;

/**
 * Обработчик для парсинга multipart. Используется при запросах от bioskud-router на выгрузву.
 */
public class MultipartBodyHandler implements Handler<RoutingContext> {
  private static final String JWT_FIELD = "params";

  private static String getJwt(List<MultipartData> parts) throws BadRequestException {
    Optional<MultipartData> found = parts.stream()
                                         .filter(p -> p.name().equalsIgnoreCase(JWT_FIELD))
                                         .findFirst();
    if (found.isPresent()) {
      return found.get().data();
    } else {
      throw new BadRequestException(ErrorApiCodesEnum.NOT_FOUND_REQUIRED_DATA,
                                    Messages.NOT_FOUND_JWT.message);
    }
  }

  @Override
  public void handle(RoutingContext routingContext) {
    MultiMap attributes = routingContext.request().formAttributes();
    List<MultipartData> data = attributes.entries().stream().map(
        attribute -> new MultipartData(attribute.getKey(), attribute.getValue())).toList();
    List<MultipartData> fileParts = data.stream().filter(t -> !t.name().contains(JWT_FIELD))
                                        .toList();
    if (fileParts.isEmpty()) {
      throw new BadRequestException(ErrorApiCodesEnum.NOT_FOUND_REQUIRED_DATA,
                                    Messages.NOT_FOUND_FILES.message);
    }

    routingContext.put(ContextData.DATA.key, fileParts);
    String jwt = getJwt(data);
    routingContext.put(ContextData.JWT_TOKEN.key, jwt);

    routingContext.next();
  }
}
