package ru.rtlabs.ebs.reference.receiver.client.agree.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rtlabs.ebs.reference.receiver.jwt.base.Jwt;

/**
 * Данные заголовков для формирования JWT. HEADER – описание свойств токена, в том числе описание
 * используемого алгоритма для подписи.
 *
 * @param alg алгоритм.
 * @param typ тип (всегда "JWT").
 */
public record HeadersConfig(
    @JsonProperty(value = ALG_FIELD) String alg,
    @JsonProperty(value = TYPE_FIELD) String typ

) implements Jwt.Header {

  private static final String ALG_FIELD = "alg";
  private static final String TYPE_FIELD = "typ";

}
