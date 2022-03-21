package ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Поле в jwt, содержащее в себе данные по векторам.
 *
 * @param modality  мнемоника модальности (фото, звук и т.п.).
 * @param signature подпись биометрического шаблона (БШ).
 * @param vendor    мнемоника вендора. Содержит в себе название вендора, используемую модальность
 *                  (фото, звук, т.п.), версия экстрактора.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Vectors(@JsonProperty(value = JSON.MODALITY, required = true) String modality,
                      @JsonProperty(value = JSON.SIGNATURE, required = true) VectorsSign signature,
                      @JsonProperty(value = JSON.VENDOR, required = true) String vendor) {

  @Override
  public String toString() {
    return "{\"_class\":\"ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload.Vectors\""
           + ", \"modality\": " + (modality == null ? null : '"' + modality + '"')
           + ", \"signature\": " + signature
           + ", \"vendor\": " + (vendor == null ? null : '"' + vendor + '"')
           + "}";
  }

  private static final class JSON {
    private static final String MODALITY = "modality";
    private static final String SIGNATURE = "signature";
    private static final String VENDOR = "vendor";
  }
}
