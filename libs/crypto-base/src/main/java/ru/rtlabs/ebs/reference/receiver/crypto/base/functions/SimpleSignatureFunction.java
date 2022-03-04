package ru.rtlabs.ebs.reference.receiver.crypto.base.functions;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import ru.CryptoPro.CAdES.EnvelopedSignature;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoException;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureExceptionEnum;

/**
 * Класс для получения базовой подписи (примитива).
 */
public class SimpleSignatureFunction implements SignatureFunction {
  protected final Signature signature;
  protected final String algorithm;
  protected final String provider;

  /**
   * Конструктор класса, для получения базовой подписи.
   *
   * @param provider  криптопровайдер.
   * @param algorithm алгоритм подписи в криптопровайдере.
   * @throws CryptoSignatureException в случае ошибок при получении объекта {@link
   *                                  java.security.Signature}.
   */
  public SimpleSignatureFunction(String provider, String algorithm)
      throws CryptoSignatureException {
    this.algorithm = algorithm;
    this.provider = provider;
    try {
      this.signature = Signature.getInstance(algorithm, provider);
    } catch (NoSuchAlgorithmException e) {
      throw new CryptoSignatureException(CryptoSignatureExceptionEnum.WRONG_ALGORITHM_NAME, e,
                                         algorithm);
    } catch (NoSuchProviderException e) {
      throw new CryptoSignatureException(CryptoSignatureExceptionEnum.WRONG_CRYPTOPROVIDER, e,
                                         provider);
    } catch (Exception e) {
      throw new CryptoSignatureException(CryptoSignatureExceptionEnum.UNKNOWN_ERROR_PROVIDER, e,
                                         provider, "инициализации механизмов подписи");
    }
  }

  @Override
  public byte[] sign(byte[] origin, PrivateKey privateKey, Certificate certificate)
      throws CryptoSignatureException {
    try {
      signature.initSign(privateKey);
      signature.update(origin);
      return signature.sign();
    } catch (SignatureException e) {
      throw new CryptoSignatureException(CryptoSignatureExceptionEnum.ERROR_SIGNING_INFO, e);
    } catch (InvalidKeyException e) {
      throw new CryptoSignatureException(CryptoSignatureExceptionEnum.WRONG_KEY, e);
    } catch (Exception e) {
      throw new CryptoSignatureException(CryptoSignatureExceptionEnum.UNKNOWN_ERROR_PROVIDER, e,
                                         provider, "подписи");
    }
  }

  @Override
  public boolean verify(byte[] origin, byte[] sign, Certificate certificate)
      throws CryptoException {
    try {
      signature.initVerify(certificate);
      signature.update(origin);
      return signature.verify(sign);
    } catch (InvalidKeyException e) {
      throw new CryptoSignatureException(CryptoSignatureExceptionEnum.WRONG_KEY, e);
    } catch (SignatureException e) {
      throw new CryptoSignatureException(CryptoSignatureExceptionEnum.ERROR_SIGNING_INFO, e);
    } catch (Exception e) {
      throw new CryptoSignatureException(CryptoSignatureExceptionEnum.UNKNOWN_ERROR_PROVIDER, e,
                                         provider, "проверки подписи");
    }
  }

  @Override
  public byte[] encrypt(byte[] data, Certificate certificate) throws CryptoException {
    EnvelopedSignature signature = new EnvelopedSignature();
    ByteArrayOutputStream envelopedByteArrayOutStream = new ByteArrayOutputStream();
    try {
      signature.addKeyAgreeRecipient((X509Certificate) certificate);
      signature.open(envelopedByteArrayOutStream);
      signature.update(data);
      signature.close();
      return envelopedByteArrayOutStream.toByteArray();
    } catch (Exception e) {
      throw new CryptoException("Не удалось зашифровать: %s", e);
    }
  }
}
