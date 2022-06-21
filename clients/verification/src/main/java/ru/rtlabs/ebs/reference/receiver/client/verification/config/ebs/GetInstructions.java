package ru.rtlabs.ebs.reference.receiver.client.verification.config.ebs;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Конфигурация для выполнения запроса на получение инструкций.
 *
 * @param metadata метаданные, которые будут отправлены в запросе.
 */
public record GetInstructions(
    @JsonProperty(value = JSON.METADATA, required = true) String metadata
) {
  private static final class JSON {
    private static final String METADATA = "metadata";
  }
}
