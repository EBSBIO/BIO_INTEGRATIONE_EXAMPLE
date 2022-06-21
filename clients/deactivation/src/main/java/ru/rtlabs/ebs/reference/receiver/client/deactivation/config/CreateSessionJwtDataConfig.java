package ru.rtlabs.ebs.reference.receiver.client.deactivation.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Класс конфигурации создания jwt токена доступа с помощью КриптоПро.
 *
 * @param header  Заголовок jwt токена.
 * @param payload Полезная нагрузка jwt токена.
 */
public record CreateSessionJwtDataConfig(
    @JsonProperty(value = JSON.HEADER, required = true) HeaderJwtConfig header,
    @JsonProperty(value = JSON.PAYLOAD, required = true) PayloadJwtConfig payload) {
  private static final class JSON {
    private static final String HEADER = "header";
    private static final String PAYLOAD = "payload";
  }
}
