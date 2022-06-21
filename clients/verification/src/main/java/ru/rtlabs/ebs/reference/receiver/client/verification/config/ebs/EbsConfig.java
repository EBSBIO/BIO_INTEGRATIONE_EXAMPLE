package ru.rtlabs.ebs.reference.receiver.client.verification.config.ebs;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Конфигурация для работы с ЕБС.
 *
 * @param createSession      конфигурация для создания сессии в ЕБС.
 * @param host               хост, где развернут ЕБС.
 * @param createSessionUrl   url для запросов на создание сессии.
 * @param getInstructions    конфигурация для запроса на получение инструкций.
 * @param getInstructionsUrl url для запросов на получение инструкций.
 * @param uploadUrl          url для запросов на загрузку БО.
 * @param upload             конфигурация для выполнения запросов на загрузку БО.
 * @param resultUrl          url для выполнения запросов на получение расширенного результата
 *                           верификации
 * @param result             конфигурация для выполнения запросов на получение расширенного
 *                           результата верификации.
 */
public record EbsConfig(
    @JsonProperty(value = JSON.CREATE_SESSION, required = true) CreateSession createSession,
    @JsonProperty(value = JSON.HOST, required = true) String host,
    @JsonProperty(value = JSON.CREATE_SESSION_URL, required = true) String createSessionUrl,
    @JsonProperty(value = JSON.GET_INSTRUCTIONS, required = true) GetInstructions getInstructions,
    @JsonProperty(value = JSON.GET_INSTRUCTIONS_URL, required = true) String getInstructionsUrl,
    @JsonProperty(value = JSON.UPLOAD_URL, required = true) String uploadUrl,
    @JsonProperty(value = JSON.UPLOAD, required = true) Upload upload,
    @JsonProperty(value = JSON.RESULT_URL, required = true) String resultUrl,
    @JsonProperty(value = JSON.RESULT, required = true) Result result


) {
  private static final class JSON {
    private static final String CREATE_SESSION = "create_session";
    private static final String HOST = "host";
    private static final String CREATE_SESSION_URL = "create_session_url";
    private static final String GET_INSTRUCTIONS = "get_instructions";
    private static final String GET_INSTRUCTIONS_URL = "get_instructions_url";
    private static final String UPLOAD_URL = "upload_url";
    private static final String UPLOAD = "upload";
    private static final String RESULT_URL = "result_url";
    private static final String RESULT = "result";

  }
}
