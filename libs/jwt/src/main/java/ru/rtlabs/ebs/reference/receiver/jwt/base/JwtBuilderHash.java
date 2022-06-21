package ru.rtlabs.ebs.reference.receiver.jwt.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.Security;
import java.util.Base64;
import java.util.Map;
import ru.CryptoPro.Crypto.CryptoProvider;
import ru.CryptoPro.JCSP.JCSP;
import ru.rtlabs.ebs.reference.receiver.configurations.crypto.CryptoConfig;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoException;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;
import ru.rtlabs.ebs.reference.receiver.crypto.base.functions.SimpleSignatureFunction;
import ru.rtlabs.ebs.reference.receiver.crypto.base.key.KeyLoader;
import ru.rtlabs.ebs.reference.receiver.cryptopro.loader.CryptoProKeyLoader;
import ru.rtlabs.ebs.reference.receiver.cryptopro.utils.KeyStoreUtils;

/**
 * Реализация {@link ru.rtlabs.ebs.reference.receiver.jwt.base.Jwt.Builder}.
 */
public class JwtBuilderHash implements Jwt.Builder {
  private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

  private static final String PROVIDER = JCSP.PROVIDER_NAME;
  final Map<String, Object> payloadClaims;
  final Map<String, Object> headerClaims;


  public JwtBuilderHash(Map<String, Object> payloadClaims, Map<String, Object> headerClaims) {
    this.payloadClaims = payloadClaims;
    this.headerClaims = headerClaims;

    Security.addProvider(new JCSP());
    Security.addProvider(new CryptoProvider());

  }

  @Override
  public String build(CryptoConfig cryptoConfig)
      throws CryptoSignatureException, JsonProcessingException {
    KeyStore keyStore = KeyStoreUtils.createKeyStore(cryptoConfig.containerType(), PROVIDER);

    KeyLoader keyLoader = new CryptoProKeyLoader(keyStore);

    SimpleSignatureFunction simpleSignatureFunction = new SimpleSignatureFunction(PROVIDER,
                                                                                  cryptoConfig.algorithm());

    String headerJson = JSON_MAPPER.writeValueAsString(headerClaims);
    String payloadJson = JSON_MAPPER.writeValueAsString(payloadClaims);
    Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    String header = encoder.encodeToString(headerJson.getBytes(StandardCharsets.UTF_8));
    String payload = encoder.encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));
    String content = String.format("%s.%s", header, payload);
    byte[] sign = simpleSignatureFunction.sign(content.getBytes(),
                                               keyLoader.getPrivateKey(cryptoConfig.keyId(),
                                                                       cryptoConfig.password())
                                                        .getSecond(),
                                               keyLoader.getCertificate(cryptoConfig.keyId())
                                                        .getSecond());
    String signature = encoder.encodeToString(sign).replace("=", "");
    return String.format("%s.%s", content, signature);
  }


  /**
   * Функция-обертка над шифрованием БО.
   *
   * @param cryptoConfig объект класса конфигурации, хранящий настройки криптографии
   * @param data         данные, которые будут зашифрованы
   * @return шифрованные данные
   * @throws CryptoException исключение
   */
  public byte[] encryptBO(CryptoConfig cryptoConfig, byte[] data) throws CryptoException {
    KeyStore keyStore = KeyStoreUtils.createKeyStore(cryptoConfig.containerType(), PROVIDER);

    KeyLoader keyLoader = new CryptoProKeyLoader(keyStore);

    SimpleSignatureFunction simpleSignatureFunction = new SimpleSignatureFunction(PROVIDER,
                                                                                  cryptoConfig.algorithm());
    return simpleSignatureFunction.encrypt(data, keyLoader.getCertificate(cryptoConfig.keyId())
                                                          .getSecond());
  }


  /**
   * Функция-обертка над подписанием БО.
   *
   * @param cryptoConfig объект класса конфигурации, хранящий настройки криптографии
   * @param data         данные для которых будет получена подпись
   * @return подпись
   * @throws CryptoException исключение
   */
  public String signBO(CryptoConfig cryptoConfig, byte[] data) throws CryptoException {
    KeyStore keyStore = KeyStoreUtils.createKeyStore(cryptoConfig.containerType(), PROVIDER);

    KeyLoader keyLoader = new CryptoProKeyLoader(keyStore);

    SimpleSignatureFunction simpleSignatureFunction = new SimpleSignatureFunction(PROVIDER,
                                                                                  cryptoConfig.algorithm());
    Base64.Encoder encoder = Base64.getEncoder();
    String payload = encoder.encodeToString(data).replace("=", "");

    byte[] sign = simpleSignatureFunction.sign(payload.getBytes(),
                                               keyLoader.getPrivateKey(cryptoConfig.keyId(),
                                                                       cryptoConfig.password())
                                                        .getSecond(),
                                               keyLoader.getCertificate(cryptoConfig.keyId())
                                                        .getSecond());

    return encoder.encodeToString(sign).replace("=", "");
  }
}
