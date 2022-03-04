package ru.rtlabs.ebs.reference.receiver.client.deactivation.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rtlabs.ebs.reference.receiver.jwt.base.Jwt;

import java.util.Date;

import static ru.rtlabs.ebs.reference.receiver.jwt.base.Constants.EXPIRES_AT;
import static ru.rtlabs.ebs.reference.receiver.jwt.base.Constants.ISSUED_AT;
import static ru.rtlabs.ebs.reference.receiver.jwt.base.Constants.ISSUER;
import static ru.rtlabs.ebs.reference.receiver.jwt.base.Constants.NOT_BEFORE;
import static ru.rtlabs.ebs.reference.receiver.jwt.base.Constants.SUBJECT;

/**
 * Класс данных payload'а jwt.
 *
 * @param sub    Идентификатор объекта.
 * @param iss    Поле issuer по нему определяется что за idp пришёл на сервис деактивации.
 * @param certId Идентификатор сертификата.
 * @param exp    Время в формате Unix Time, определяющее момент, когда токен станет невалидным (expiration).
 * @param iat    Время в формате Unix Time, определяющее момент, когда токен был создан. iat и nbf могут не совпадать,
 *               например, если токен был создан раньше, чем время, когда он должен стать валидным (issued at).
 * @param nbf    В противоположность ключу exp, это время в формате Unix Time, определяющее момент, когда токен
 *               станет валидным (not before).
 */
public record Payload(@JsonProperty(value = JSON.SUB, required = true) String sub,
                      @JsonProperty(value = JSON.ISS, required = true) String iss,
                      @JsonProperty(value = JSON.CERT_ID, required = true) String certId,
                      @JsonProperty(value = JSON.EXP, required = true) long exp,
                      @JsonProperty(value = JSON.IAT, required = true) long iat,
                      @JsonProperty(value = JSON.NBF, required = true) long nbf) implements Jwt.Payload {
  private static final class JSON {
    private static final String SUB = SUBJECT;
    private static final String ISS = ISSUER;
    private static final String CERT_ID = "cert_id";
    private static final String EXP = EXPIRES_AT;
    private static final String IAT = ISSUED_AT;
    private static final String NBF = NOT_BEFORE;
  }
}
