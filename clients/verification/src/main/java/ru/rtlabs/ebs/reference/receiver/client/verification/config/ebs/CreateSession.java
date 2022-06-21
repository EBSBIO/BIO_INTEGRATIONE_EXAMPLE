package ru.rtlabs.ebs.reference.receiver.client.verification.config.ebs;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rtlabs.ebs.reference.receiver.client.verification.config.ebs.jwt.JwtData;
import ru.rtlabs.ebs.reference.receiver.client.verification.config.ebs.jwt.create.session.HeaderCreateSession;
import ru.rtlabs.ebs.reference.receiver.client.verification.config.ebs.jwt.create.session.PayloadCreateSession;

/**
 * Конфигурация для выполнения запросов на создание верификационной сессии в ЕБС.
 *
 * @param jwtData  данные из которых будет собран токен доступа(jwt) для запроса, если стоит флаг
 *                 {@link
 *                 ru.rtlabs.ebs.reference.receiver.client.verification.config.VerificationConfig#isGenerateJwt()}
 * @param jwt      уже готовый токен доступа(jwt) для выполнения запроса, будет использоваться, если
 *                 стоит false
 *                 {@link
 *                 ru.rtlabs.ebs.reference.receiver.client.verification.config.VerificationConfig#isGenerateJwt()}
 * @param metadata метаданные, которые будут отправлены в запросе.
 */
public record CreateSession(
    @JsonProperty(
        value = JSON.CREATE_SESSION_JWT_DATA) JwtData<HeaderCreateSession, PayloadCreateSession> jwtData,
    @JsonProperty(value = JSON.CREATE_SESSION_JWT) String jwt,
    @JsonProperty(value = JSON.METADATA, required = true) String metadata

) {
  private static final class JSON {
    private static final String METADATA = "metadata";
    private static final String CREATE_SESSION_JWT_DATA = "create_session_jwt_data";
    private static final String CREATE_SESSION_JWT = "create_session_jwt";
  }
}
