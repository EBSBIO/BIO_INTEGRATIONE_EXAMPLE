package ru.rtlabs.ebs.reference.receiver.client.agree.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rtlabs.ebs.reference.receiver.configurations.crypto.CryptoConfig;


/**
 * Конфигурация модуля.
 *
 * @param jwtObject    объект, содержащий JWT, в случае, если выбран запуск модуля с использованием
 *                     готового JWT, переданном в конфигурационном файле.
 * @param generalData  объект, содержащий основные настройки для запуска модуля.
 * @param payloadData  объект, содержащий данные для формирования payload jwt.
 * @param headersData  объект, содержащий данные для формирования headers jwt.
 * @param cryptoConfig объект, содержащий данные для работы с криптосервисом.
 */
public record AgreeConfig(
    @JsonProperty(value = JWT_OBJECT_FIELD) JwtConfig jwtObject,
    @JsonProperty(value = GENERAL_DATA_FIELD, required = true) GeneralConfig generalData,
    @JsonProperty(value = PAYLOAD_DATA_FIELD) PayloadConfig payloadData,
    @JsonProperty(value = HEADERS_FIELD, required = true) HeadersConfig headersData,
    @JsonProperty(value = CRYPTO_CONFIG_FIELD, required = true) CryptoConfig cryptoConfig
) {

  private static final String JWT_OBJECT_FIELD = "existing_jwt";
  private static final String GENERAL_DATA_FIELD = "general_data";
  private static final String PAYLOAD_DATA_FIELD = "payload_data";
  private static final String HEADERS_FIELD = "headers_data";
  private static final String CRYPTO_CONFIG_FIELD = "crypto_config";

}


