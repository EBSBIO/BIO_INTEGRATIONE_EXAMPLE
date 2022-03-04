package ru.rtlabs.ebs.reference.receiver.cryptopro.utils;

import java.security.KeyStore;
import ru.CryptoPro.JCSP.JCSP;
import ru.rtlabs.ebs.reference.receiver.configurations.crypto.CryptoConfig;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;
import ru.rtlabs.ebs.reference.receiver.crypto.base.functions.SimpleSignatureFunction;
import ru.rtlabs.ebs.reference.receiver.crypto.base.key.KeyLoader;
import ru.rtlabs.ebs.reference.receiver.cryptopro.loader.CryptoProKeyLoader;

public class Helper {
  private static final String PROVIDER = JCSP.PROVIDER_NAME;

  public static KeyLoader getKeyLoader(CryptoConfig cryptoConfig) throws CryptoSignatureException {
    KeyStore keyStore = KeyStoreUtils.createKeyStore(cryptoConfig
                                                         .containerType(), PROVIDER);

    KeyLoader keyLoader = new CryptoProKeyLoader(keyStore);
    return keyLoader;
  }

  public static SimpleSignatureFunction getSimpleSignatureFunction(CryptoConfig cryptoConfig)
      throws CryptoSignatureException {
    SimpleSignatureFunction simpleSignatureFunction = new SimpleSignatureFunction(PROVIDER,
                                                                                  cryptoConfig.algorithm());
    return simpleSignatureFunction;
  }
}
