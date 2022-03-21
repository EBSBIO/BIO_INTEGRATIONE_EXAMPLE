package ru.rtlabs.ebs.reference.receiver.handlers;

import io.vertx.ext.web.RoutingContext;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.rtlabs.ebs.reference.receiver.exception.BadRequestException;
import ru.rtlabs.ebs.reference.receiver.exception.ErrorApiCodesEnum;
import ru.rtlabs.ebs.reference.receiver.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.BaseHeader;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.BasePayload;
import ru.rtlabs.ebs.reference.receiver.service.jwt.BaseValidator;
import ru.rtlabs.ebs.reference.receiver.service.jwt.ValidatorDeactivation;
import ru.rtlabs.ebs.reference.receiver.service.jwt.ValidatorUpload;
import ru.rtlabs.ebs.reference.receiver.service.utils.ContextData;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class BioskudRouterJwtParserHandlerTest {
  @DataProvider(name = "jwtAndValidator")
  private static Object[][] jwtAndValidatorProvider() {
    return new Object[][] {
        {"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzb21lX3VzZXJfNzc3IiwiYXVkIjoiVEVTVF9JRFAiLCJ2ZWN0b3JzIjpbeyJ2ZW5kb3IiOiJzdHViX3Bob3RvXzEuMC4wIiwibW9kYWxpdHkiOiJwaG90byIsInNpZ25hdHVyZSI6eyJkYXRhIjoiUEhvVEhIV250SkNIbDdCNXI2UnE3cXpPVWo5empnN3FRMXhKYjN4QXRaYUc2THRMWXhpRzFXd2x2TVJpMElJaWlQUVdRMG9XelVDK0dXZWRiU2NiSWc9PSIsImFsZyI6IkdPU1QzNDExXzIwMTJfMjU2d2l0aEdPU1QzNDEwXzIwMTJfMjU2IiwidHlwZSI6IlBMQUlOIiwia2V5SWQiOiJpbW1vcnRhbC1zZWZzaWduZWQtR29zdDIwMTIta2V5In19XSwiaWF0IjoxNjQ2OTg5NjAyLCJleHAiOjMyMjU3MTI3MjAyfQo.MIAGCSqGSIb3DQEHAqCAMIACAQExDjAMBggqhQMHAQECAgUAMIAGCSqGSIb3DQEHAaCAJIAEggIrZXdvZ0lDSjJaWElpT2lBeExBb2dJQ0owZVhBaU9pQWlTbGRVSWl3S0lDQWlZV3huSWpvZ0lrZFBVMVF6TkRFd1h6SXdNVEpmTWpVMklnb0tmUW8uZXlKemRXSWlPaUp6YjIxbFgzVnpaWEpmTnpjM0lpd2lZWFZrSWpvaVZFVlRWRjlKUkZBaUxDSjJaV04wYjNKeklqcGJleUoyWlc1a2IzSWlPaUp6ZEhWaVgzQm9iM1J2WHpFdU1DNHdJaXdpYlc5a1lXeHBkSGtpT2lKd2FHOTBieUlzSW5OcFoyNWhkSFZ5WlNJNmV5SmtZWFJoSWpvaVVFaHZWRWhJVjI1MFNrTkliRGRDTlhJMlVuRTNjWHBQVldvNWVtcG5OM0ZSTVhoS1lqTjRRWFJhWVVjMlRIUk1XWGhwUnpGWGQyeDJUVkpwTUVsSmFXbFFVVmRSTUc5WGVsVkRLMGRYWldSaVUyTmlTV2M5UFNJc0ltRnNaeUk2SWtkUFUxUXpOREV4WHpJd01USmZNalUyZDJsMGFFZFBVMVF6TkRFd1h6SXdNVEpmTWpVMklpd2lkSGx3WlNJNklsQk1RVWxPSWl3aWEyVjVTV1FpT2lKcGJXMXZjblJoYkMxelpXWnphV2R1WldRdFIyOXpkREl3TVRJdGEyVjVJbjE5WFN3aWFXRjBJam94TmpRMk9UZzVOakF5TENKbGVIQWlPak15TWpVM01USTNNakF5ZlFvAAAAAAAAMYIBXzCCAVsCAQEwgY4wdjELMAkGA1UEBhMCUlUxDzANBgNVBAgMBlJ1c3NpYTEPMA0GA1UEBwwGTW9zY293MRIwEAYDVQQKDAlTdXBlclBsYXQxFTATBgNVBAsMDFN1cGVyUGxhdCBDQTEaMBgGA1UEAwwRU3VwZXJQbGF0IENBIFJvb3QCFHXxzGdg6YQ4wLlz7NCNCmuXo97VMAwGCCqFAwcBAQICBQCgaTAYBgkqhkiG9w0BCQMxCwYJKoZIhvcNAQcBMBwGCSqGSIb3DQEJBTEPFw0yMjAzMTEwOTIyMjNaMC8GCSqGSIb3DQEJBDEiBCAVXDXodopOu-2AzbTDRPkhkN7A5uJuAW8JOxkQplstXDAKBggqhQMHAQEDAgRAJfOT_THXXgJMtulJs6okRxicFT9EZfmcNurN4yU49lO2Tqvo-u0IsZP5m7wh0KDGbu-fnE9VAtg-YU27tNv9dAAAAAAAAA",
         new ValidatorUpload()},
        {"ewogICJ0eXAiOiAiSldUIiwKICAiYWxnIjogIkdPU1QzNDEwXzIwMTJfMjU2IgoKfQo.eyJzdWIiOiJzb21lX3VzZXJfNzc3IiwiYXVkIjoiVEVTVF9JRFAiLCJpYXQiOjE2NDY5ODk2MDIsImV4cCI6MzIyNTcxMjcyMDJ9Cg.MIAGCSqGSIb3DQEHAqCAMIACAQExDjAMBggqhQMHAQECAgUAMIAGCSqGSIb3DQEHAaCAJIAEgapld29nSUNKMGVYQWlPaUFpU2xkVUlpd0tJQ0FpWVd4bklqb2dJa2RQVTFRek5ERXdYekl3TVRKZk1qVTJJZ29LZlFvLmV5SnpkV0lpT2lKemIyMWxYM1Z6WlhKZk56YzNJaXdpWVhWa0lqb2lWRVZUVkY5SlJGQWlMQ0pwWVhRaU9qRTJORFk1T0RrMk1ESXNJbVY0Y0NJNk16SXlOVGN4TWpjeU1ESjlDZwAAAAAAADGCAV8wggFbAgEBMIGOMHYxCzAJBgNVBAYTAlJVMQ8wDQYDVQQIDAZSdXNzaWExDzANBgNVBAcMBk1vc2NvdzESMBAGA1UECAgwJU3VwZXJQbGF0MRUwEwYDVQQLDAxTdXBlclBsYXQgQ0ExGjAYBgNVBAMMEVN1cGVyUGxhdCBDQSBSb290AhR18cxnYOmEOMC5c-zQjQprl6Pe1TAMBggqhQMHAQECAgUAoGkwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMjIwMzE2MTIwNzQ0WjAvBgkqhkiG9w0BCQQxIgQgqEehJvH_aV2pR6t0sjGFHUIUs6P8wUG7zbav2emuZ5UwCgYIKoUDBwEBAwIEQIYj3wEtDz1CM7grAVOOw7_ca9mhd5IDtN_4I6YdtXLwWLLP2N6vOiYI0tMC8UQU4dUoAWQogX2WZoyKOGKtAfEAAAAAAAA%",
         new ValidatorDeactivation()},};
  }

  @Test(dataProvider = "jwtAndValidator")
  public <H extends BaseHeader, P extends BasePayload> void testPositiveHandle(String jwt,
                                                                               BaseValidator<H, P> validator) {
    // GIVEN
    var mockedContext = Mockito.mock(RoutingContext.class);
    when(mockedContext.get(ContextData.JWT_TOKEN.key)).thenReturn(jwt);

    var handler = new BioskudRouterJwtParserHandler<>(validator);
    // WHEN
    handler.handle(mockedContext);
    // THEN
    verify(mockedContext).next();
    verify(mockedContext).put(eq(ContextData.JWT_OBJECT.key), any());
    verify(mockedContext).get(ContextData.JWT_TOKEN.key);
    verify(mockedContext, never()).fail(any());
  }

  @DataProvider(name = "invalidJwtSizeAndValidator")
  private static Object[][] invalidJwtSizeAndValidatorProvider() {
    return new Object[][] {{"header.payload", new ValidatorUpload()},
                           {"header.payload", new ValidatorDeactivation()},
                           {"header", new ValidatorUpload()},
                           {"header", new ValidatorDeactivation()},
                           {"headerю.payload.sign.something", new ValidatorUpload()},
                           {"headerю.payload.sign.something", new ValidatorDeactivation()},};
  }

  @DataProvider(name = "emptyJwtAndValidator")
  private static Object[][] emptyJwtAndValidatorProvider() {
    return new Object[][] {{"", new ValidatorUpload()}, {"", new ValidatorDeactivation()},
                           {null, new ValidatorUpload()}, {null, new ValidatorDeactivation()},};
  }

  @DataProvider(name = "invalidJwtAndValidator")
  private static Object[][] invalidJwtAndValidatorProvider() {
    return new Object[][] {
        // не корректный payload
        {"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
         new ValidatorUpload()},
        {"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
         new ValidatorDeactivation()},
        // не корректный header
        {"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.wrJ__8Q_6BcB2ug9370TBuK5JoAjErqsQtYf7aLcFBk",
         new ValidatorUpload()},
        {"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.wrJ__8Q_6BcB2ug9370TBuK5JoAjErqsQtYf7aLcFBk",
         new ValidatorDeactivation()},};
  }

  @DataProvider(name = "emptySingJwtAndValidator")
  private static Object[][] emptySingJwtAndValidatorProvider() {
    return new Object[][] {
        {"ewogICJ2ZXIiOiAxLAogICJ0eXAiOiAiSldUIiwKICAiYWxnIjogIkdPU1QzNDEwXzIwMTJfMjU2IgoKfQo.eyJzdWIiOiJzb21lX3VzZXJfNzc3IiwiYXVkIjoiVEVTVF9JRFAiLCJ2ZWN0b3JzIjpbeyJ2ZW5kb3IiOiJzdHViX3Bob3RvXzEuMC4wIiwibW9kYWxpdHkiOiJwaG90byIsInNpZ25hdHVyZSI6eyJkYXRhIjoiUEhvVEhIV250SkNIbDdCNXI2UnE3cXpPVWo5empnN3FRMXhKYjN4QXRaYUc2THRMWXhpRzFXd2x2TVJpMElJaWlQUVdRMG9XelVDK0dXZWRiU2NiSWc9PSIsImFsZyI6IkdPU1QzNDExXzIwMTJfMjU2d2l0aEdPU1QzNDEwXzIwMTJfMjU2IiwidHlwZSI6IlBMQUlOIiwia2V5SWQiOiJpbW1vcnRhbC1zZWZzaWduZWQtR29zdDIwMTIta2V5In19XSwiaWF0IjoxNjQ2OTg5NjAyLCJleHAiOjMyMjU3MTI3MjAyfQo. ",
         new ValidatorUpload()},
        {"ewogICJ0eXAiOiAiSldUIiwKICAiYWxnIjogIkdPU1QzNDEwXzIwMTJfMjU2IgoKfQo.eyJzdWIiOiJzb21lX3VzZXJfNzc3IiwiYXVkIjoiVEVTVF9JRFAiLCJpYXQiOjE2NDY5ODk2MDIsImV4cCI6MzIyNTcxMjcyMDJ9Cg. ",
         new ValidatorDeactivation()},};
  }

  @Test(dataProvider = "invalidJwtSizeAndValidator",
        description = "Проверяем кейс, когда получен невалидный Jwt по размеру, jwt должен содержать 3 части: заголовок, пейлоад и подпись, которые разделяются символом \".\"")
  public <H extends BaseHeader, P extends BasePayload> void testNegativeHandleInvalidJwtSize(
      String jwt, BaseValidator<H, P> validator) {
    // GIVEN
    var mockedContext = Mockito.mock(RoutingContext.class);
    when(mockedContext.get(ContextData.JWT_TOKEN.key)).thenReturn(jwt);

    var handler = new BioskudRouterJwtParserHandler<>(validator);
    try {
      // WHEN
      handler.handle(mockedContext);
    } catch (Exception e) {
      // THEN
      verify(mockedContext, never()).next();
      verify(mockedContext, never()).put(eq(ContextData.JWT_OBJECT.key), any());
      verify(mockedContext).get(ContextData.JWT_TOKEN.key);
      if (e instanceof BadRequestException badRequestException) {
        assertEquals(badRequestException.getError(), ErrorApiCodesEnum.BAD_REQUEST);
        assertEquals(badRequestException.getMessage(), Messages.ERROR_JWT_PART_SIZE.message);
      } else {
        fail("Получена неожиданная ошибка");
      }
    }
  }

  @Test(dataProvider = "emptyJwtAndValidator",
        description = "Проверяем кейс, когда получен невалидный Jwt: пустой или null")
  public <H extends BaseHeader, P extends BasePayload> void testNegativeHandleEmptyJwt(String jwt,
                                                                                       BaseValidator<H, P> validator) {
    // GIVEN
    var mockedContext = Mockito.mock(RoutingContext.class);
    when(mockedContext.get(ContextData.JWT_TOKEN.key)).thenReturn(jwt);

    var handler = new BioskudRouterJwtParserHandler<>(validator);
    try {
      // WHEN
      handler.handle(mockedContext);
    } catch (Exception e) {
      // THEN
      verify(mockedContext, never()).next();
      verify(mockedContext, never()).put(eq(ContextData.JWT_OBJECT.key), any());
      verify(mockedContext).get(ContextData.JWT_TOKEN.key);
      if (e instanceof BadRequestException badRequestException) {
        assertEquals(badRequestException.getError(), ErrorApiCodesEnum.BAD_REQUEST);
        assertEquals(badRequestException.getMessage(), Messages.ERROR_JWT_IS_EMPTY.message);
      } else {
        fail("Получена неожиданная ошибка");
      }
    }
  }

  @Test(dataProvider = "invalidJwtAndValidator",
        description = "Проверяем кейс, когда получен невалидный Jwt: пустой или null")
  public <H extends BaseHeader, P extends BasePayload> void testNegativeHandleInvalidJwt(String jwt,
                                                                                         BaseValidator<H, P> validator) {
    // GIVEN
    var mockedContext = Mockito.mock(RoutingContext.class);
    when(mockedContext.get(ContextData.JWT_TOKEN.key)).thenReturn(jwt);

    var handler = new BioskudRouterJwtParserHandler<>(validator);
    try {
      // WHEN
      handler.handle(mockedContext);
    } catch (Exception e) {
      // THEN
      verify(mockedContext, never()).next();
      verify(mockedContext, never()).put(eq(ContextData.JWT_OBJECT.key), any());
      verify(mockedContext).get(ContextData.JWT_TOKEN.key);
      if (e instanceof BadRequestException badRequestException) {
        assertEquals(badRequestException.getError(), ErrorApiCodesEnum.BAD_REQUEST);
        assertEquals(badRequestException.getMessage(), Messages.INVALID_JWT.message);
      } else {
        fail("Получена неожиданная ошибка");
      }

    }
  }

  @Test(dataProvider = "emptySingJwtAndValidator",
        description = "Проверяем кейс, когда получен не валидный Jwt: пустой или null")
  public <H extends BaseHeader, P extends BasePayload> void testNegativeHandleEmptySignJwt(
      String jwt, BaseValidator<H, P> validator) {
    // GIVEN
    var mockedContext = Mockito.mock(RoutingContext.class);
    when(mockedContext.get(ContextData.JWT_TOKEN.key)).thenReturn(jwt);

    var handler = new BioskudRouterJwtParserHandler<>(validator);
    try {
      // WHEN
      handler.handle(mockedContext);
    } catch (Exception e) {
      // THEN
      verify(mockedContext, never()).next();
      verify(mockedContext, never()).put(eq(ContextData.JWT_OBJECT.key), any());
      verify(mockedContext).get(ContextData.JWT_TOKEN.key);
      if (e instanceof BadRequestException badRequestException) {
        assertEquals(badRequestException.getError(), ErrorApiCodesEnum.BAD_REQUEST);
        assertEquals(badRequestException.getMessage(), Messages.ERROR_JWT_SIGN_EMPTY.message);
      } else {
        fail("Получена неожиданная ошибка");
      }

    }

  }
}
