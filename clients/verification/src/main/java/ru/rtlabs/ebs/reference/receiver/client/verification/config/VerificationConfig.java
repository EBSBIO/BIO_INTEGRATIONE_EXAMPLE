package ru.rtlabs.ebs.reference.receiver.client.verification.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rtlabs.ebs.reference.receiver.client.verification.config.ebs.EbsConfig;
import ru.rtlabs.ebs.reference.receiver.configurations.crypto.CryptoConfig;

/**
 * Общая конфигурация для модуля.
 *
 * @param cryptoConfig  конфигурация для работы с сервисом cryptopro.
 * @param ebsConfig     конфигурация для работы с ebs.
 * @param isGenerateJwt флаг на формирование jwt из конфигов(true) посредством сервиса шифрования.
 */
public record VerificationConfig(
    @JsonProperty(value = JSON.CRYPTO_CONFIG) CryptoConfig cryptoConfig,
    @JsonProperty(value = JSON.EBS_CONFIG, required = true) EbsConfig ebsConfig,
    @JsonProperty(value = JSON.IS_GENERATE_JWT, required = true) boolean isGenerateJwt
) {
  private static final class JSON {
    private static final String CRYPTO_CONFIG = "crypto_config";
    private static final String EBS_CONFIG = "ebs_config";
    private static final String IS_GENERATE_JWT = "is_generate_jwt";
  }
}
