package ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions;

/**
 * Единый базовый класс исключений для всех представленных исключений в проекте, связанных с
 * криптой.
 */
public class CryptoException extends Exception {
  public CryptoException(String message) {
    super(message);
  }

  public CryptoException(String message, Throwable e) {
    super(message, e);
  }
}
