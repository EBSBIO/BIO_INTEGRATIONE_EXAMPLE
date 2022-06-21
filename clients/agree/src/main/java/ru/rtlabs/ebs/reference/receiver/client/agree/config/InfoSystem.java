package ru.rtlabs.ebs.reference.receiver.client.agree.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Содержит информацию о ИС Контрагента, отправившей запрос.
 *
 * @param systemId   мнемоника ИС Контрагента.
 * @param contractId идентификатор контракта.
 * @param raId       идентификатор центра обслуживания в реестре поставщика идентификации IDP.
 * @param employeeId сотрудник, осуществляющий регистрацию.
 * @param certId     id сертификата, которым подписана JWT (сертификаты хранятся в реестре
 *                   сертификатов в привязке к мнемонике ИС_Поставщика БДн).
 */
public record InfoSystem(
    @JsonProperty(value = SYSTEM_ID_FIELD, required = true) String systemId,
    @JsonProperty(value = CONTRACT_ID_FIELD, required = true) String contractId,
    @JsonProperty(value = RA_ID_FIELD) String raId,
    @JsonProperty(value = EMPLOYEE_ID_FIELD) String employeeId,
    @JsonProperty(value = CERT_ID_FIELD, required = true) String certId

) {

  private static final String SYSTEM_ID_FIELD = "system_id";
  private static final String CONTRACT_ID_FIELD = "contract_id";
  private static final String RA_ID_FIELD = "ra_id";
  private static final String EMPLOYEE_ID_FIELD = "employee_id";
  private static final String CERT_ID_FIELD = "cert_id";

}
