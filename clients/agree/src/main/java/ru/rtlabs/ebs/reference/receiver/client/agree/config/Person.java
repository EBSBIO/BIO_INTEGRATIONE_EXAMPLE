package ru.rtlabs.ebs.reference.receiver.client.agree.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Содержит информацию о пользователе.
 *
 * @param userId  ID УЗ пользователя IDP.
 * @param idp     идентификатор (мнемоника) IDP.
 * @param contact контактные данные пользователя.
 */
public record Person(
    @JsonProperty(value = USER_ID_FIELD, required = true) String userId,
    @JsonProperty(value = IDP_FIELD, required = true) String idp,
    @JsonProperty(value = CONTACT_FIELD) Contact contact
) {

  private static final String CONTACT_FIELD = "contact";
  private static final String USER_ID_FIELD = "user_id";
  private static final String IDP_FIELD = "idp";

}
