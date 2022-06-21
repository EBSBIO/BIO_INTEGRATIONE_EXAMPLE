package ru.rtlabs.ebs.reference.receiver.client.agree.service;


import java.net.URL;

/**
 * Сервис для отправки запроса на agree.
 */
public interface IAgreeService {
  /**
   * Отправка запроса на agree.
   *
   * @param url адрес, куда отправлять запрос.
   * @param jwt сформированная jwt для отправки в запросе.
   */
  void sendRegistration(URL url, String jwt);
}
