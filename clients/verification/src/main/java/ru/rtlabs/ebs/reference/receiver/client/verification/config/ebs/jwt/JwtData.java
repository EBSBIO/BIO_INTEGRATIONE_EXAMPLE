package ru.rtlabs.ebs.reference.receiver.client.verification.config.ebs.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rtlabs.ebs.reference.receiver.jwt.base.Jwt;

/**
 * Данные для генерации jwt.
 *
 * @param header  заголовок jwt.
 * @param payload payload jwt.
 * @param <H>     тип заголовка.
 * @param <P>     тип payload.
 */
public record JwtData<H extends Jwt.Header, P extends Jwt.Payload>(
    @JsonProperty(value = JSON.HEADER, required = true) H header,
    @JsonProperty(value = JSON.PAYLOAD, required = true) P payload
) {

  private static final class JSON {
    private static final String HEADER = "header";
    private static final String PAYLOAD = "payload";
  }
}
