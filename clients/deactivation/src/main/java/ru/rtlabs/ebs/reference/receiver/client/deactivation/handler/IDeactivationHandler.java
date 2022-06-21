package ru.rtlabs.ebs.reference.receiver.client.deactivation.handler;

import java.io.IOException;

public interface IDeactivationHandler {

  /**
   * Запуск эталонного запроса на деактивацию УЗ.
   *
   * @throws IOException Исключение ввода/вывода.
   */
  void startProcess() throws IOException;
}
