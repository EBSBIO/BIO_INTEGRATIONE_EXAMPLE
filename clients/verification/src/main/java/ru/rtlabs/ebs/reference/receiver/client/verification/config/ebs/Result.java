package ru.rtlabs.ebs.reference.receiver.client.verification.config.ebs;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rtlabs.ebs.reference.receiver.client.verification.config.ebs.jwt.JwtData;
import ru.rtlabs.ebs.reference.receiver.client.verification.config.ebs.jwt.result.HeaderResult;
import ru.rtlabs.ebs.reference.receiver.client.verification.config.ebs.jwt.result.PayloadResult;

/**
 * Конфигурация для выполнения запроса на получение расширенного результата верификации.
 *
 * @param jwtData данные из которых будет собран токен доступа(jwt) для запроса, если стоит флаг
 *                {@link
 *                ru.rtlabs.ebs.reference.receiver.client.verification.config.VerificationConfig#isGenerateJwt()}
 * @param jwt     уже готовый токен доступа(jwt) для выполнения запроса, будет использоваться, если
 *                стоит false
 *                {@link
 *                ru.rtlabs.ebs.reference.receiver.client.verification.config.VerificationConfig#isGenerateJwt()}
 */
public record Result(
    @JsonProperty(
        value = JSON.RESULT_JWT_DATA) JwtData<HeaderResult, PayloadResult> jwtData,
    @JsonProperty(value = JSON.RESULT_JWT) String jwt

) {
  private static final class JSON {
    private static final String RESULT_JWT = "result_jwt";
    private static final String RESULT_JWT_DATA = "result_jwt_data";
  }
}
