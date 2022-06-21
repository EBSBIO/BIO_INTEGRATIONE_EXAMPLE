package ru.rtlabs.ebs.reference.receiver.jwt.base;

/**
 * Заранее определенные поля jwt.
 */
public class Constants {
  //Header
  /**
   * Обязательное поле.
   */
  public static final String ALGORITHM = "alg";
  /**
   * Обязательное поле.
   */
  public static final String TYPE = "typ";
  public static final String KEY_ID = "kid";
  public static final String CLAIMS = "claims";

  //Payload
  public static final String ISSUER = "iss";
  public static final String SUBJECT = "sub";
  public static final String EXPIRES_AT = "exp";
  public static final String NOT_BEFORE = "nbf";
  public static final String ISSUED_AT = "iat";
  public static final String JWT_ID = "jti";
  public static final String AUDIENCE = "aud";
  public static final String CLIENT_ID = "client_id";
  public static final String VERIFY_TOKEN = "verify_token";
  public static final String RESULT = "result";
  public static final String MATCH = "match";
}
