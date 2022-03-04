package ru.rtlabs.ebs.reference.receiver.client.deactivation.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import static ru.rtlabs.ebs.reference.receiver.jwt.base.Constants.ALGORITHM;
import static ru.rtlabs.ebs.reference.receiver.jwt.base.Constants.TYPE;

/**
 * Класс конфигурации заголовка jwt токена.
 *
 * @param ver Версия токена доступа,
 * @param typ Тип токена.
 * @param alg Алгоритм формирования электронной подписи.
 * @param sbt Тип маркера доступа.
 */
public record HeaderJwtConfig(
    @JsonProperty(value = VER) int ver,
    @JsonProperty(value = TYP, required = true) String typ,
    @JsonProperty(value = SBT) String sbt,
    @JsonProperty(value = ALG, required = true) String alg) {
  private static final String VER = "ver";
  private static final String TYP = TYPE;
  private static final String SBT = "sbt";
  private static final String ALG = ALGORITHM;
}
