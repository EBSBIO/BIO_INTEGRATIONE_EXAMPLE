package ru.rtlabs.ebs.reference.receiver.service.utils;

public enum Status {

  OK(200);

  private final int code;

  Status(final int statusCode) {
    this.code = statusCode;
  }

  public static String ok() {
    return String.valueOf(OK.getCode());
  }

  public int getCode() {
    return code;
  }
}
