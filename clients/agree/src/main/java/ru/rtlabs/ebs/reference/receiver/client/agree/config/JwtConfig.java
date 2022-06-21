package ru.rtlabs.ebs.reference.receiver.client.agree.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Содержит готовый JWT из конфигурационного файла.
 *
 * @param jwt данный JWT токен состоит из трёх частей, разделённых точкой, и имеет следующий вид:
 *            HEADER.PAYLOAD.SIGNATURE. Каждая из частей токена представляет собой Base64url
 *            Encoding значение.
 */
public record JwtConfig(
    @JsonProperty(value = JWT_FIELD, required = true) String jwt
) {

  private static final String JWT_FIELD = "jwt";

}
