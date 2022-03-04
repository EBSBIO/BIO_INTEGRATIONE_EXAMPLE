package ru.rtlabs.ebs.reference.receiver.client.verification.config.ebs.jwt.create.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rtlabs.ebs.reference.receiver.jwt.base.Jwt;

/**
 * Заголовок jwt для создания сессии.
 *
 * @param alg алгоритм подписи.
 * @param typ тип подписи.
 */
public record HeaderCreateSession(@JsonProperty(value = JSON.TYP, required = true) String typ,
                                  @JsonProperty(value = JSON.ALG, required = true) String alg
) implements Jwt.Header {
  private static final class JSON {
    private static final String TYP = "typ";
    private static final String ALG = "alg";
  }
}
