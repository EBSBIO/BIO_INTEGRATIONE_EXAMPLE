package ru.rtlabs.ebs.reference.receiver.marshalling;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Объект хранит в себе данные, которые будут отправлены в качестве ответа при ошибках.
 *
 * @param code         код ошибки.
 * @param errorMessage сообщение ошибки.
 */
public record ErrorResponseData(@JsonProperty(JSON.CODE) Integer code,
                                @JsonProperty(JSON.MESSAGE) String errorMessage) {

  @Override
  public String toString() {
    return "{\"_class\":\"ru.rtlabs.ebs.reference.receiver.marshalling.ErrorResponseData\""
           + ", \"code\": " + code
           + ", \"errorMessage\": " + (errorMessage == null ? null : '"' + errorMessage + '"')
           + "}";
  }

  private static final class JSON {

    private static final String MESSAGE = "message";
    private static final String CODE = "code";
  }
}
