package ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.BaseHeader;


/**
 * Header jwt при запросе на выгрузку.
 *
 * @param alg  алгоритм подписи.
 * @param type тип подписи.
 */
public record HeaderUploadJwt(@JsonProperty(value = JSON.ALG_FIELD, required = true) String alg,
                              @JsonProperty(value = JSON.TYPE_FIELD, required = true) String type)
    implements BaseHeader {

  @Override
  public String toString() {
    return "{\"_class\":\"ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload.HeaderUploadJwt\""
           + ", \"alg\": " + (alg == null ? null : '"' + alg + '"')
           + ", \"type\": " + (type == null ? null : '"' + type + '"')
           + "}";
  }

  private static final class JSON {
    private static final String ALG_FIELD = "alg";
    private static final String TYPE_FIELD = "typ";
  }
}
