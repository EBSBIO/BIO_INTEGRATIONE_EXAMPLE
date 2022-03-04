package ru.rtlabs.ebs.reference.receiver.cryptopro.loader;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import ru.CryptoPro.JCP.KeyStore.JCPPrivateKeyEntry;
import ru.CryptoPro.JCP.params.JCPProtectionParameter;
import ru.rtlabs.ebs.reference.receiver.base.Pair;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureExceptionEnum;
import ru.rtlabs.ebs.reference.receiver.crypto.base.key.KeyLoader;

/**
 * Класс отвечает за получение закрытого ключа и сертификата с помощью механизмов КриптоПРО.
 */
public final class CryptoProKeyLoader implements KeyLoader {
  private final KeyStore keyStore;

  public CryptoProKeyLoader(KeyStore keyStore) {
    this.keyStore = keyStore;
  }

  @Override
  public Pair<String, PrivateKey> getPrivateKey(String keyId, String password)
      throws CryptoSignatureException {
    return new Pair<>(keyId, loadPrivateKey(keyId, password));
  }

  @Override
  public Pair<String, Certificate> getCertificate(String keyId)
      throws CryptoSignatureException {
    return new Pair<>(keyId, loadCertificate(keyId));
  }

  private PrivateKey loadPrivateKey(String keyId, String password) throws CryptoSignatureException {
    JCPPrivateKeyEntry entry = getEntry(keyStore, keyId, password);
    return entry.getPrivateKey();
  }

  private static JCPPrivateKeyEntry getEntry(KeyStore keyStore, String keyId, String keyPassword)
      throws CryptoSignatureException {
    KeyStore.ProtectionParameter pp = new JCPProtectionParameter(keyPassword.toCharArray());
    try {
      return (JCPPrivateKeyEntry) keyStore.getEntry(keyId, pp);
    } catch (NoSuchAlgorithmException | KeyStoreException e) {
      throw new CryptoSignatureException(CryptoSignatureExceptionEnum.ERROR_OPENING_KEYSTORE, e);
    } catch (UnrecoverableEntryException e) {
      throw new CryptoSignatureException(CryptoSignatureExceptionEnum.MISSING_KEY_IN_STORE, e,
                                         keyId);
    } catch (Exception e) {
      throw new CryptoSignatureException(CryptoSignatureExceptionEnum.UNKNOWN_ERROR_PROVIDER, e,
                                         "JCSP", String.format(
          "во время получения ключевой пары с идентификатором %s", keyId));
    }
  }

  private Certificate loadCertificate(String keyId) throws CryptoSignatureException {
    try {
      return keyStore.getCertificate(keyId);
    } catch (KeyStoreException e) {
      throw new CryptoSignatureException(CryptoSignatureExceptionEnum.MISSING_KEY_IN_STORE, e,
                                         keyId);
    } catch (Exception e) {
      throw new CryptoSignatureException(CryptoSignatureExceptionEnum.UNKNOWN_ERROR_PROVIDER, e,
                                         "JCSP", String.format(
          "получения сертификата с идентификатором %s из ключевой пары", keyId));
    }
  }
}
