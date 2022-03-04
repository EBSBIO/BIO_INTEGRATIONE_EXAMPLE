package ru.rtlabs.ebs.reference.receiver.base.exceptions;

/**
 * Ошибка при запуске модуля.
 */
public class StartModuleException extends Exception {
  public StartModuleException(String message, Throwable cause) {
    super(message, cause);
  }
}
