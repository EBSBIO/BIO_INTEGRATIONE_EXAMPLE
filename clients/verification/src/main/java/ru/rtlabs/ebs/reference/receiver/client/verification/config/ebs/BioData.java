package ru.rtlabs.ebs.reference.receiver.client.verification.config.ebs;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Информация по БО, которая будет отправлена на верификацию.
 *
 * @param path        путь до биометрических данных.
 * @param contentType Content-type биометрических данных.
 * @param fileName    наименование файла.
 */
public record BioData(
    @JsonProperty(value = JSON.PATH, required = true) String path,
    @JsonProperty(value = JSON.CONTENT_TYPE) String contentType,
    @JsonProperty(value = JSON.FILE_NAME) String fileName
) {
  private static final class JSON {
    private static final String PATH = "path";
    private static final String CONTENT_TYPE = "content_type";
    private static final String FILE_NAME = "file_name";
  }
}
