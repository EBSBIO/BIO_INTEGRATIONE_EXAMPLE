package ru.rtlabs.ebs.reference.receiver.client.deactivation.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rtlabs.ebs.reference.receiver.configurations.crypto.CryptoConfig;

/**
 * Класс конфигурации деактивации.
 *
 * @param cryptoConfig         Набор конфигураций криптосервиса.
 * @param base                 Набор базовых конфигураций сервиса эталонных запросов деактивации УЗ.
 * @param createSessionJwt     Данные сформированного jwt токена.
 * @param createSessionJwtData Набор конфигураций для формирования jwt токена с помощью КриптоПро.
 */
public record DeactivationConfig(
    @JsonProperty(value = JSON.CRYPTO_CONFIG, required = true) CryptoConfig cryptoConfig,
    @JsonProperty(value = JSON.BASE_CONFIG, required = true) BaseConfig base,
    @JsonProperty(value = JSON.CREATE_SESSION_JWT) CreateSessionJwtConfig createSessionJwt,
    @JsonProperty(value = JSON.CREATE_SESSION_JWT_DATA) CreateSessionJwtDataConfig createSessionJwtData) {
  private static final class JSON {
    private static final String CRYPTO_CONFIG = "crypto_config";
    private static final String BASE_CONFIG = "base";
    private static final String CREATE_SESSION_JWT = "create_session_jwt";
    private static final String CREATE_SESSION_JWT_DATA = "create_session_jwt_data";
  }
}

