package ru.rtlabs.ebs.reference.receiver.client.agree.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Основные параметры работы модуля agree.
 *
 * @param needGenerateToken использовать готовый токен из конфигурации (false) или генерить из
 *                          переданных данных (true).
 * @param serviceUrl        - URL agree.
 * @param registrationPath  - путь для запроса регистрации без биометрии
 */
public record GeneralConfig(

    @JsonProperty(value = NEED_GENERATE_TOKEN_FIELD, required = true) boolean needGenerateToken,
    @JsonProperty(value = SERVICE_URL_FIELD, required = true) String serviceUrl,
    @JsonProperty(value = REGISTRATION_PATH_FIELD, required = true) String registrationPath
) {

  private static final String NEED_GENERATE_TOKEN_FIELD = "need_generate_token";
  private static final String SERVICE_URL_FIELD = "service_url";
  private static final String REGISTRATION_PATH_FIELD = "registration_path";

}
