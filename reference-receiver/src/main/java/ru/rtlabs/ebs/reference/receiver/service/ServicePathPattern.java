package ru.rtlabs.ebs.reference.receiver.service;

/**
 * Список endpoint-ов запросов сервиса.
 */
public enum ServicePathPattern {
  /**
   * GET-запрос - проверка работоспособности сервиса.
   */
  HEALTH("/api/health"),
  /**
   * Обработка уведомлений о создании УЗ.
   */
  IDP_NOTIFIER("/api/idp_notifier"),
  /**
   * Обработка уведомлений о мэтчинге УЗ.
   */
  MATCHER_NOTIFIER("/api/matcher_notifier"),
  /**
   * Обработка запроса деактивации шаблонов.
   */
  BIOSKUD_ROUTER_DEACTIVATION("/api/templates/deactivation"),
  /**
   * Обработка запроса приема шаблонов на выгрузку.
   */
  BIOSKUD_ROUTER_UPLOAD("/api/templates/upload");

  /**
   * Строка с endpoint-ом запроса.
   */
  public final String value;

  ServicePathPattern(String value) {
    this.value = value;
  }
}
