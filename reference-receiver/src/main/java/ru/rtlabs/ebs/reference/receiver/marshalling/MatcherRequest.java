package ru.rtlabs.ebs.reference.receiver.marshalling;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Представляет собой dto тела запроса к сервису получения результатов мэтчинга.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MatcherRequest(
    @JsonProperty(value = JSON.REQUEST_ID, required = true) String requestId,
    @JsonProperty(value = JSON.UID, required = true) String userId,
    @JsonProperty(JSON.STU) MatcherRequest.Status stu) {

  @Override
  public String toString() {
    return "{\"_class\":\"ru.rtlabs.ebs.reference.receiver.marshalling.MatcherRequest\""
           + ", \"requestId\": " + (requestId == null ? null : '"' + requestId + '"')
           + ", \"userId\": " + (userId == null ? null : '"' + userId + '"')
           + ", \"stu\": " + stu
           + "}";
  }

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public enum Status {
    MA, MF
  }

  private static final class JSON {

    private static final String UID = "user_id";
    private static final String REQUEST_ID = "request_id";
    private static final String STU = "stu";
  }
}
