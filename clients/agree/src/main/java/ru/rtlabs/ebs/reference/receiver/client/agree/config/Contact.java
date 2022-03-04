package ru.rtlabs.ebs.reference.receiver.client.agree.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Контактные данные пользователя.
 *
 * @param phone телефон пользователя, указывается в формате +7XXXXXXXXXX, где X цифра от 0 до 9.
 * @param email электронная почта пользователя.
 */
public record Contact(
    @JsonProperty(value = PHONE_FIELD) String phone,
    @JsonProperty(value = EMAIL_FIELD) String email
) {

  private static final String PHONE_FIELD = "phone";
  private static final String EMAIL_FIELD = "email";

}
