package ru.rtlabs.ebs.reference.receiver.cryptopro.utils;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureExceptionEnum;

public class KeyStoreUtils {
  private KeyStoreUtils() {
  }

  public static KeyStore createKeyStore(String containerName, String provider)
      throws CryptoSignatureException {
    try {
      KeyStore keyStore = KeyStore.getInstance(containerName, provider);
      keyStore.load(null, null);
      return keyStore;
    } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
      throw new CryptoSignatureException(CryptoSignatureExceptionEnum.ERROR_OPENING_KEYSTORE, e,
                                         containerName);
    } catch (NoSuchProviderException e) {
      throw new CryptoSignatureException(CryptoSignatureExceptionEnum.WRONG_CRYPTOPROVIDER, e,
                                         provider);
    } catch (Exception e) {
      throw new CryptoSignatureException(CryptoSignatureExceptionEnum.UNKNOWN_ERROR_PROVIDER, e,
                                         provider,
                                         "попытки получить хранилище ключевых контейнеров");
    }
  }
}
