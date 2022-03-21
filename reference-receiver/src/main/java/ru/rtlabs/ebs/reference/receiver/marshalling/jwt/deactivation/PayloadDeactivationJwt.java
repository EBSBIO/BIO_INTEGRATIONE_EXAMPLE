package ru.rtlabs.ebs.reference.receiver.marshalling.jwt.deactivation;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.BasePayload;


/**
 * Payload jwt при запросе на деактивацию.
 *
 * @param sub идентификатор пользователя.
 * @param aud имя (мнемоника) проекта.
 * @param iat время создания JWT.
 * @param exp время протухания JWT.
 */
public record PayloadDeactivationJwt(
    @JsonProperty(value = JSON.SUB_FIELD, required = true) String sub,
    @JsonProperty(value = JSON.AUD_FIELD, required = true) String aud,
    @JsonProperty(value = JSON.IAT_FIELD, required = true) String iat,
    @JsonProperty(value = JSON.EXP_FIELD, required = true) String exp)
    implements BasePayload {

  @Override
  public String toString() {
    return
        "{\"_class\":\"ru.rtlabs.ebs.reference.receiver.marshalling.jwt.deactivation.PayloadDeactivationJwt\""
        + ", \"sub\": " + (sub == null ? null : '"' + sub + '"')
        + ", \"aud\": " + (aud == null ? null : '"' + aud + '"')
        + ", \"iat\": " + (iat == null ? null : '"' + iat + '"')
        + ", \"exp\": " + (exp == null ? null : '"' + exp + '"')
        + "}";
  }

  private static final class JSON {
    private static final String SUB_FIELD = "sub";
    private static final String AUD_FIELD = "aud";
    private static final String IAT_FIELD = "iat";
    private static final String EXP_FIELD = "exp";
  }
}
