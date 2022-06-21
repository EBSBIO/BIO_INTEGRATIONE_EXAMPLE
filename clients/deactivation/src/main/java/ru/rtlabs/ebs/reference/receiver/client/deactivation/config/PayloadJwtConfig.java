package ru.rtlabs.ebs.reference.receiver.client.deactivation.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import static ru.rtlabs.ebs.reference.receiver.jwt.base.Constants.EXPIRES_AT;
import static ru.rtlabs.ebs.reference.receiver.jwt.base.Constants.ISSUED_AT;
import static ru.rtlabs.ebs.reference.receiver.jwt.base.Constants.ISSUER;
import static ru.rtlabs.ebs.reference.receiver.jwt.base.Constants.NOT_BEFORE;
import static ru.rtlabs.ebs.reference.receiver.jwt.base.Constants.SUBJECT;

/**
 * Класс конфигурации полезной нагрузки jwt токена.
 *
 * @param iss    Поле issuer по нему определяется что за idp пришёл на сервис деактивации.
 * @param sub    Идентификатор объекта.
 * @param certId Идентификатор сертификата.
 * @param nbf    Время в формате Unix Time, определяющее момент, когда токен станет валидным (not before).
 * @param exp    Время в формате Unix Time, определяющее момент, когда токен станет невалидным (expiration).
 * @param iat    Время в формате Unix Time, определяющее момент, когда токен был создан.
 */
public record PayloadJwtConfig(
    @JsonProperty(value = ISS, required = true) String iss,
    @JsonProperty(value = SUB, required = true) String sub,
    @JsonProperty(value = CERT_ID, required = true) String certId,
    @JsonProperty(value = NBF) long nbf,
    @JsonProperty(value = EXP, required = true) long exp,
    @JsonProperty(value = IAT, required = true) long iat) {
  private static final String ISS = ISSUER;
  private static final String SUB = SUBJECT;
  private static final String CERT_ID = "cert_id";
  private static final String NBF = NOT_BEFORE;
  private static final String EXP = EXPIRES_AT;
  private static final String IAT = ISSUED_AT;
}
