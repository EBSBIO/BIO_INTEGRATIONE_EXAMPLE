package ru.rtlabs.ebs.reference.receiver.service.jwt;

import ru.rtlabs.ebs.reference.receiver.exception.BadRequestException;
import ru.rtlabs.ebs.reference.receiver.exception.ErrorApiCodesEnum;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.BaseJwt;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload.HeaderUploadJwt;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload.PayloadUploadJwt;

public class ValidatorUpload extends BaseValidator<HeaderUploadJwt, PayloadUploadJwt> {

  public ValidatorUpload() {
    super(HeaderUploadJwt.class, PayloadUploadJwt.class);
  }

  @Override
  public BaseJwt<HeaderUploadJwt, PayloadUploadJwt> parserJwt(String jwtString)
      throws BadRequestException {
    BaseJwt<HeaderUploadJwt, PayloadUploadJwt> jwt = parser(jwtString);
    HeaderUploadJwt header = jwt.header();
    PayloadUploadJwt payload = jwt.payload();
    if (header.alg() == null || header.alg().isBlank()
        || header.type() == null || header.type().isBlank()
        || payload.vectors() == null || payload.vectors().isEmpty()
        || payload.sub() == null || payload.sub().isBlank()
        || payload.aud() == null || payload.aud().isBlank()
        || payload.iat() == null || payload.iat().isBlank()
        || payload.exp() == null || payload.exp().isBlank()) {
      throw new BadRequestException(ErrorApiCodesEnum.BAD_REQUEST);
    }
    return new BaseJwt<>(header, payload);
  }
}

