
package ru.rtlabs.ebs.reference.receiver.handlers.base;

import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.RoutingContext;
import java.util.ArrayList;
import java.util.Base64;
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

  private static String getJwt(MultiMap attributes) throws BadRequestException {
    Optional<MultipartData> found = attributes.entries().stream()
                                              .filter(p -> p.getKey().equalsIgnoreCase(JWT_FIELD))
                                              .map(
                                                  attribute -> new MultipartData(attribute.getKey(),
                                                                                 attribute.getValue()))
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
    List<MultipartData> fileParts = new ArrayList<>();
    routingContext.fileUploads()
                  .forEach(file -> fileParts.add(new MultipartData(file.name(),
                                                                   Base64.getEncoder().encodeToString(
                                                                       routingContext.vertx().fileSystem()
                                                                                     .readFileBlocking(
                                                                                         file.uploadedFileName())
                                                                                     .getBytes()))));
    if (fileParts.isEmpty()) {
      throw new BadRequestException(ErrorApiCodesEnum.NOT_FOUND_REQUIRED_DATA,
                                    Messages.NOT_FOUND_FILES.message);
    }
    MultiMap attributes = routingContext.request().formAttributes();
    String jwt = getJwt(attributes);

    routingContext.put(ContextData.DATA.key, fileParts);
    routingContext.put(ContextData.JWT_TOKEN.key, jwt);

    routingContext.next();
  }
}
