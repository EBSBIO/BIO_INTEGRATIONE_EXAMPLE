package ru.rtlabs.ebs.reference.receiver.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Конфигурация сервиса.
 *
 * @param serverPort порт, на котором будет запущен сервис.
 */
public record ReferenceReceiverConfig(
    @JsonProperty(value = PORT_FIELD, required = true) int serverPort,
    @JsonProperty(value = BEARER_FIELD, required = true) String authBearer) {
  private static final String PORT_FIELD = "server_port";
  private static final String BEARER_FIELD = "auth_bearer";
}
