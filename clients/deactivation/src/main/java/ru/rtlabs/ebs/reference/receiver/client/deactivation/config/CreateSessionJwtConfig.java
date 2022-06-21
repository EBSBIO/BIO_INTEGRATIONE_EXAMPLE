package ru.rtlabs.ebs.reference.receiver.client.deactivation.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Класс сформированного jwt токена доступа.
 *
 * @param jwt Сформированный jwt токен доступа.
 */
public record CreateSessionJwtConfig(
    @JsonProperty(value = JWT, required = true) String jwt) {
  private static final String JWT = "jwt";
}
