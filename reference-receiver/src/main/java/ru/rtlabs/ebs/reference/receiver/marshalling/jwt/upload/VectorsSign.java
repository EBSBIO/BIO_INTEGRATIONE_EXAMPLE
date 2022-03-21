package ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Objects;

/**
 * Поле в jwt, содержащее в себе данные по подписи векторов.
 *
 * @param alg   алгоритм ключа для подписи.
 * @param data  base64 подпись биометрического шаблона (БШ).
 * @param keyId идентификатор ключа.
 * @param type  тип подписи.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record VectorsSign(@JsonProperty(value = JSON.ALD, required = true) String alg,
                          @JsonProperty(value = JSON.DATA, required = true) byte[] data,
                          @JsonProperty(value = JSON.KEY_ID, required = true) String keyId,
                          @JsonProperty(value = JSON.TYPE, required = true) String type) {

  @Override
  public String toString() {
    return "{\"_class\":\"ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload.VectorsSign\""
           + ", \"alg\": " + (alg == null ? null : '"' + alg + '"')
           + ", \"data\": " + (data == null ? null : "{\"blob\": {\"size\": " + data.length + "}}")
           + ", \"keyId\": " + (keyId == null ? null : '"' + keyId + '"')
           + ", \"type\": " + (type == null ? null : '"' + type + '"')
           + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VectorsSign that = (VectorsSign) o;
    return Objects.equals(alg, that.alg) && Arrays.equals(data, that.data)
           && Objects.equals(keyId, that.keyId) && Objects.equals(type,
                                                                  that.type);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(alg, keyId, type);
    result = 31 * result + Arrays.hashCode(data);
    return result;
  }

  private static final class JSON {
    private static final String ALD = "alg";
    private static final String DATA = "data";
    private static final String KEY_ID = "keyId";
    private static final String TYPE = "type";
  }
}
