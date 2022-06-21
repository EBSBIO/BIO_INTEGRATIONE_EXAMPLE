package ru.rtlabs.ebs.reference.receiver.client.agree.config;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Содержит данные для мэтчинга.
 *
 * @param key   описание передаваемых данных для мэтчинга.  Может принимать значения: "hash" - Хеш
 *              ПДн, "{мнемоника IDP}" - IDP.
 * @param value значения данных для мэтчинга (Хеш ПДн или ID УЗ пользователя IDP).
 */
public record MatchingData(
    @JsonProperty(value = KEY_FIELD, required = true) String key,
    @JsonProperty(value = VALUE_FIELD, required = true) String value
) {

  private static final String KEY_FIELD = "key";
  private static final String VALUE_FIELD = "value";

}
