package ru.rtlabs.ebs.reference.receiver.client.agree.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import ru.rtlabs.ebs.reference.receiver.jwt.base.Jwt;

/**
 * Payload для формирования JWT.
 *
 * @param serviceType  тип (мнемоника) услуги.
 * @param datimeTz     дата регистрации (Формат Unix time stamp в секундах).
 * @param infoSystem   информация о ИС Контрагента, отправившей запрос.
 * @param agree        информация о согласии.
 * @param person       информация о пользователе.
 * @param matchingData данные для мэтчинга.
 * @param meta         дополнительные метаданные.
 * @param metrics      дополнительные данные о метриках системы.
 */
public record PayloadConfig(
    @JsonProperty(value = SERVICE_TYPE_FIELD, required = true) String serviceType,
    @JsonProperty(value = DATETIME_TZ_FIELD, required = true) String datimeTz,
    @JsonProperty(value = INFO_SYSTEM_FIELD, required = true) InfoSystem infoSystem,
    @JsonProperty(value = AGREE_FIELD) Agree agree,
    @JsonProperty(value = PERSON_FIELD, required = true) Person person,
    @JsonProperty(value = MATCHING_FIELD, required = true) List<MatchingData> matchingData,
    @JsonProperty(value = META_FIELD) Map<String, Object> meta,
    @JsonProperty(value = METRICS_FIELD) Map<String, Object> metrics

) implements Jwt.Payload {

  private static final String SERVICE_TYPE_FIELD = "service_type";
  private static final String DATETIME_TZ_FIELD = "datetime_tz";
  private static final String INFO_SYSTEM_FIELD = "infosystem";
  private static final String AGREE_FIELD = "agree";
  private static final String PERSON_FIELD = "person";
  private static final String MATCHING_FIELD = "matching";
  private static final String META_FIELD = "meta";
  private static final String METRICS_FIELD = "metrics";

}
