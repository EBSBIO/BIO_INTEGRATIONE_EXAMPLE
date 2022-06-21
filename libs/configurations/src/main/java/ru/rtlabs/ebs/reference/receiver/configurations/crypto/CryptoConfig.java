package ru.rtlabs.ebs.reference.receiver.configurations.crypto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Конфигурация для работы с сервисами подписания.
 *
 * @param containerType - наименование контейнера.
 * @param keyId         - идентификатор контейнера.
 * @param password      - пароль для контейнера.
 * @param algorithm     - алгоритм для подписания данных.
 */
public record CryptoConfig(
    @NotNull @JsonProperty(value = JSON.CONTAINER_TYPE, required = true) String containerType,
    @NotNull @JsonProperty(value = JSON.KEY_ID, required = true) String keyId,
    @NotNull @JsonProperty(value = JSON.PASSWORD, required = true) String password,
    @NotNull @JsonProperty(value = JSON.ALGORITHM, required = true) String algorithm
) {
  private static final class JSON {
    private static final String CONTAINER_TYPE = "container_type";
    private static final String KEY_ID = "key_id";
    private static final String PASSWORD = "password";
    private static final String ALGORITHM = "algorithm";
  }
}
