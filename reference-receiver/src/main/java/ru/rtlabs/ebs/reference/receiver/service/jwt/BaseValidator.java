package ru.rtlabs.ebs.reference.receiver.service.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.exception.BadRequestException;
import ru.rtlabs.ebs.reference.receiver.exception.ErrorApiCodesEnum;
import ru.rtlabs.ebs.reference.receiver.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.BaseHeader;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.BaseJwt;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.BasePayload;


/**
 * Класс для валидации JWT. Проверка зависит от типа header и payload.
 *
 * @param <H> объект для header.
 * @param <P> объект для payload.
 */
public abstract class BaseValidator<H extends BaseHeader, P extends BasePayload> {

  protected static final int JWT_PART_SIZE = 3;
  private static final Logger LOGGER = LoggerFactory.getLogger(BaseValidator.class);
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private final Class<H> headerClass;
  private final Class<P> payloadClass;

  BaseValidator(Class<H> headerClass, Class<P> payloadClass) {
    this.headerClass = headerClass;
    this.payloadClass = payloadClass;
  }

  /**
   * Проверка, что обязательные поля в JWT заполнены.
   *
   * @param jwt полученный JWT.
   * @throws BadRequestException обязательные поля пустые или отсутствуют.
   */
  public abstract BaseJwt<H, P> parserJwt(String jwt) throws BadRequestException;

  protected BaseJwt<H, P> parser(String jwt) throws BadRequestException {
    LOGGER.info(Messages.CHECK_JWT_VALUE.message);
    if (jwt == null || jwt.isBlank()) {
      throw new BadRequestException(ErrorApiCodesEnum.BAD_REQUEST,
                                    Messages.ERROR_JWT_IS_EMPTY.message);
    }

    String[] parts = jwt.split("\\.", 0);
    if (parts.length != JWT_PART_SIZE) {
      throw new BadRequestException(ErrorApiCodesEnum.BAD_REQUEST,
                                    Messages.ERROR_JWT_PART_SIZE.message);
    }

    byte[] encodedHeader = Base64.getUrlDecoder().decode(parts[0]);
    byte[] encodedPayload = Base64.getUrlDecoder().decode(parts[1]);
    String encodedSign = parts[2];

    if (encodedSign == null || encodedSign.isBlank()) {
      throw new BadRequestException(ErrorApiCodesEnum.BAD_REQUEST,
                                    Messages.ERROR_JWT_SIGN_EMPTY.message);
    }
    try {
      H header = OBJECT_MAPPER.readValue(encodedHeader, headerClass);
      P payload = OBJECT_MAPPER.readValue(encodedPayload, payloadClass);
      return new BaseJwt<>(header, payload);
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
      throw new BadRequestException(ErrorApiCodesEnum.BAD_REQUEST, Messages.INVALID_JWT.message);
    }
  }
}
