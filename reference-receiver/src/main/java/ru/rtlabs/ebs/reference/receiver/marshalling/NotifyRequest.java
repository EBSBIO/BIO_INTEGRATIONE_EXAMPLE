package ru.rtlabs.ebs.reference.receiver.marshalling;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Запрос на нотификацию IDP о статусе БКШ пользователя.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record NotifyRequest(@JsonProperty(value = JSON.UID, required = true) String userId,
                            @JsonProperty(value = JSON.STU,
                                          required = true) NotifyRequest.Status stu) {

  @Override
  public String toString() {
    return "{\"_class\":\"ru.rtlabs.ebs.reference.receiver.marshalling.NotifyRequest\""
           + ", \"userId\": " + (userId == null ? null : '"' + userId + '"')
           + ", \"stu\": " + stu
           + "}";
  }

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public enum Status {
    A, B, D, F
  }

  private static final class JSON {

    private static final String UID = "user_id";
    private static final String STU = "stu";
  }
}
