package ru.rtlabs.ebs.reference.receiver.jwt.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Base64;
import ru.CryptoPro.JCSP.JCSP;
import ru.rtlabs.ebs.reference.receiver.configurations.crypto.CryptoConfig;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;
import ru.rtlabs.ebs.reference.receiver.crypto.base.functions.SimpleSignatureFunction;
import ru.rtlabs.ebs.reference.receiver.crypto.base.key.KeyLoader;
import ru.rtlabs.ebs.reference.receiver.cryptopro.utils.Helper;

/**
 * Небольшая реализация {@link Jwt.Builder}, взятая из кода ЕБС и немного реструктурированная.
 */
public class JwtBuilder implements Jwt.Builder {
  private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

  private static final String PROVIDER = JCSP.PROVIDER_NAME;
  final Jwt.Payload payloadClaims;
  final Jwt.Header headerClaims;

  public JwtBuilder(Jwt.Payload payloadClaims, Jwt.Header headerClaims) {
    this.payloadClaims = payloadClaims;
    this.headerClaims = headerClaims;
  }

  @Override
  public String build(CryptoConfig cryptoConfig)
      throws CryptoSignatureException, JsonProcessingException {

    Security.addProvider(new JCSP());

    KeyLoader keyLoader = Helper.getKeyLoader(cryptoConfig);

    SimpleSignatureFunction simpleSignatureFunction = Helper.getSimpleSignatureFunction(
        cryptoConfig);

    String headerJson = JSON_MAPPER.writeValueAsString(headerClaims);
    String payloadJson = JSON_MAPPER.writeValueAsString(payloadClaims);
    Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    String header = encoder.encodeToString(headerJson.getBytes(StandardCharsets.UTF_8));
    String payload = encoder.encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));
    String content = String.format("%s.%s", header, payload);
    byte[] sign = simpleSignatureFunction.sign(content.getBytes(),
                                               keyLoader.getPrivateKey(
                                                            cryptoConfig.keyId(),
                                                            cryptoConfig.password())
                                                        .getSecond(),
                                               keyLoader.getCertificate(
                                                            cryptoConfig.keyId())
                                                        .getSecond());
    String signature = encoder.encodeToString(sign);
    return String.format("%s.%s", content, signature);
  }
}

