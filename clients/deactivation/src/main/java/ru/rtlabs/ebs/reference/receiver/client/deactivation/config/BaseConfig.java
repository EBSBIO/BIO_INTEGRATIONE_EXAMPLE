package ru.rtlabs.ebs.reference.receiver.client.deactivation.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Класс основных настроек деактивации.
 *
 * @param isJwtFromConfig Формирование jwt из конфигов (true) или из параметров (false)
 * @param requestId       Уникальный идентификатор сообщения, сохраняется неизменным для всех сообщений транзакции.
 * @param host            Адрес сервиса деактивации УЗ
 */
public record BaseConfig(@JsonProperty(value = IS_JWT_CONFIG, required = true) boolean isJwtFromConfig,
                         @JsonProperty(value = REQUEST_ID, required = true) String requestId,
                         @JsonProperty(value = HOST, required = true) String host) {
  private static final String IS_JWT_CONFIG = "is_jwt_config";
  private static final String REQUEST_ID = "request_id";
  private static final String HOST = "host";
}
