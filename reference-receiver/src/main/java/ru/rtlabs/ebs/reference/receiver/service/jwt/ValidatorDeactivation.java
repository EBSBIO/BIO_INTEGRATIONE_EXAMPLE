package ru.rtlabs.ebs.reference.receiver.service.jwt;

import ru.rtlabs.ebs.reference.receiver.exception.BadRequestException;
import ru.rtlabs.ebs.reference.receiver.exception.ErrorApiCodesEnum;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.BaseJwt;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.deactivation.HeaderDeactivationJwt;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.deactivation.PayloadDeactivationJwt;


public class ValidatorDeactivation
    extends BaseValidator<HeaderDeactivationJwt, PayloadDeactivationJwt> {

  public ValidatorDeactivation() {
    super(HeaderDeactivationJwt.class, PayloadDeactivationJwt.class);
  }

  @Override
  public BaseJwt<HeaderDeactivationJwt, PayloadDeactivationJwt> parserJwt(String jwtString)
      throws BadRequestException {
    BaseJwt<HeaderDeactivationJwt, PayloadDeactivationJwt> jwt = parser(jwtString);
    HeaderDeactivationJwt header = jwt.header();
    PayloadDeactivationJwt payload = jwt.payload();
    if (header.alg() == null || header.alg().isBlank()
        || header.type() == null || header.type().isBlank()
        || payload.sub() == null || payload.sub().isBlank()
        || payload.aud() == null || payload.aud().isBlank()
        || payload.iat() == null || payload.iat().isBlank()
        || payload.exp() == null || payload.exp().isBlank()) {
      throw new BadRequestException(ErrorApiCodesEnum.BAD_REQUEST);
    }
    return new BaseJwt<>(header, payload);


  }
}
