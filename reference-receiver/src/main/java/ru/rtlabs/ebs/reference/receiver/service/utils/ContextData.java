package ru.rtlabs.ebs.reference.receiver.service.utils;

public enum ContextData {
  SESSION_ID("__tnx_id"),
  JWT_TOKEN("__jwt_field"),
  DATA("__data_field"),
  JWT_OBJECT("__jwt_object");

  public final String key;

  ContextData(String key) {
    this.key = key;
  }
}
