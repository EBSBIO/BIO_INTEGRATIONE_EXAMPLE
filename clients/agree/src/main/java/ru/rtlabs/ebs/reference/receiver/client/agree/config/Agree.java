package ru.rtlabs.ebs.reference.receiver.client.agree.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Объект содержит информацию о согласии.
 *
 * @param agreementId идентификатор записи данных согласия в ИС КА. Если согласие для ИС КА получает
 *                    ЕБС, то заполняется идентификатор согласия в ЕБС.
 * @param dateFrom    дата c которой действует согласие (Формат Unix timestamp в секундах).
 * @param dateTo      дата до которой действует согласие (Формат Unix timestamp в секундах).
 */
public record Agree(
    @JsonProperty(value = AGREEMENT_ID_FIELD) String agreementId,
    @JsonProperty(value = DATE_FROM_FIELD, required = true) String dateFrom,
    @JsonProperty(value = DATE_TO_FIELD) String dateTo
) {

  private static final String AGREEMENT_ID_FIELD = "agreement_id";
  private static final String DATE_FROM_FIELD = "date_from";
  private static final String DATE_TO_FIELD = "date_to";

}
