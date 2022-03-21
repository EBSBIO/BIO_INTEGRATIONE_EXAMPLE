package ru.rtlabs.ebs.reference.receiver.handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.exception.BadRequestException;
import ru.rtlabs.ebs.reference.receiver.exception.ErrorApiCodesEnum;
import ru.rtlabs.ebs.reference.receiver.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.marshalling.MultipartData;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.BaseJwt;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload.HeaderUploadJwt;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload.PayloadUploadJwt;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload.Vectors;
import ru.rtlabs.ebs.reference.receiver.service.utils.ContextData;

/**
 * Хэндлер для проверки содержимого мультипарта - проверка векторов, при запросе bioskud-router на
 * выгрузку.
 */
public class BioskudRouterValidateVectorsHandler implements Handler<RoutingContext> {
  private static final Logger LOGGER = LoggerFactory.getLogger(
      BioskudRouterValidateVectorsHandler.class);

  @Override
  public void handle(RoutingContext routingContext) {
    LOGGER.info(Messages.CHECK_VECTORS.message);
    BaseJwt<HeaderUploadJwt, PayloadUploadJwt> jwt = routingContext.get(
        ContextData.JWT_OBJECT.key);
    List<MultipartData> fileParts = routingContext.get(ContextData.DATA.key);
    List<String> vendorsName = fileParts.stream().map(MultipartData::name).toList();

    for (Vectors vector : jwt.payload().vectors()) {
      String vendorName = vector.vendor();
      if (!vendorsName.contains(vendorName)) {
        LOGGER
            .error(Messages.ERROR_INVALID_VECTORS.message, vendorName);
        throw new BadRequestException(ErrorApiCodesEnum.BAD_REQUEST,
                                      Messages.ERROR_PARSE_REQUEST.message);
      }
    }
    routingContext.next();
  }
}
