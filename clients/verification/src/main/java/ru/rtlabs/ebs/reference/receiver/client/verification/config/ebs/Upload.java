package ru.rtlabs.ebs.reference.receiver.client.verification.config.ebs;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Конфигурация для выполнения запроса на загрузку БО для верификации.
 *
 * @param metadata  метаданные, которые будут отправлены в запросе.
 * @param photoData БО по фото.
 */
public record Upload(
    @JsonProperty(value = JSON.METADATA, required = true) String metadata,
    @JsonProperty(value = JSON.PHOTO_DATA) BioData photoData
) {
  private static final class JSON {
    private static final String METADATA = "metadata";
    private static final String PHOTO_DATA = "photo_data";
  }
}
