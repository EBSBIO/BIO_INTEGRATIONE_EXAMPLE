package ru.rtlabs.ebs.reference.receiver.client.verification.config.ebs.jwt.create.session;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import ru.rtlabs.ebs.reference.receiver.jwt.base.Jwt;

/**
 * Payload jwt для создания сессии.
 *
 * @param iat      время в формате Unix Time, определяющее момент, когда токен был создан.
 * @param sub      идентификатор УЗ пользователя IDP (чувствительная к регистру строка или URI,
 *                 которая является уникальным идентификатором стороны, о которой содержится
 *                 информация в данном токене).
 * @param aud      массив чувствительных к регистру строк или URI, являющийся списком получателей
 *                 данного токена. Когда принимающая сторона получает JWT с данным ключом, она
 *                 должна проверить наличие себя в получателях — иначе проигнорировать токен
 *                 (audience).
 * @param exp      время в формате Unix Time, определяющее момент, когда токен станет не валидным
 *                 (expiration).
 * @param nbf      время в формате Unix Time, ранее которого нельзя использовать токен.
 * @param iss      мнемоника IDP (чувствительная к регистру строка или URI, которая является
 *                 уникальным идентификатором стороны, генерирующей токен).
 * @param clientId мнемоника Потребителя БДн.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PayloadCreateSession(@JsonProperty(value = JSON.IAT, required = true) long iat,
                                   @JsonProperty(value = JSON.SUB, required = true) String sub,
                                   @JsonProperty(value = JSON.AUD) List<String> aud,
                                   @JsonProperty(value = JSON.EXP, required = true) long exp,
                                   @JsonProperty(value = JSON.NBF) long nbf,
                                   @JsonProperty(value = JSON.ISS, required = true) String iss,
                                   @JsonProperty(value = JSON.CLIENT_ID,
                                                 required = true) String clientId
) implements Jwt.Payload {
  private static final class JSON {
    private static final String IAT = "iat";
    private static final String SUB = "sub";
    private static final String AUD = "aud";
    private static final String EXP = "exp";
    private static final String NBF = "nbf";
    private static final String ISS = "iss";
    private static final String CLIENT_ID = "client_id";
  }
}
