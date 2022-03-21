package ru.rtlabs.ebs.reference.receiver.handlers;

import io.vertx.ext.web.RoutingContext;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.rtlabs.ebs.reference.receiver.exception.BadRequestException;
import ru.rtlabs.ebs.reference.receiver.exception.ErrorApiCodesEnum;
import ru.rtlabs.ebs.reference.receiver.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.marshalling.MatcherRequest;
import ru.rtlabs.ebs.reference.receiver.marshalling.NotifyRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BodyParserHandlerTest {


  @DataProvider(name = "invalidBody")
  private static Object[][] invalidBodyProvider() {
    return new Object[][] {
        {null, MatcherRequest.class},
        {"", MatcherRequest.class},
        {null, NotifyRequest.class},
        {"", NotifyRequest.class},
        };
  }

  @Test(description = "Проверяем кейс, когда получен валидный запрос от idp-notifier")
  public void testPositiveHandleIdpNotifier() {
    // GIVEN
    var request = """
        {
          "user_id": "1000453364",
          "stu": "A"
        }
        """;
    var mockedContext = Mockito.mock(RoutingContext.class);
    when(mockedContext.getBodyAsString()).thenReturn(request);

    var handler = new BodyParserHandler<>(NotifyRequest.class);
    // WHEN
    handler.handle(mockedContext);
    // THEN
    assertsPositiveCase(mockedContext);
  }

  @Test(description = "Проверяем кейс, когда получен не валидный запрос от idp-notifier")
  public void testNegativeHandleIdpNotifier() {
    // GIVEN
    var request = """
        {
          "stu": "A"
        }
        """;
    var mockedContext = Mockito.mock(RoutingContext.class);
    when(mockedContext.getBodyAsString()).thenReturn(request);

    var handler = new BodyParserHandler<>(NotifyRequest.class);
    // WHEN
    handler.handle(mockedContext);
    // THEN
    assertsNegativeCase(mockedContext);
  }

  @Test(description = "Проверяем кейс, когда получен валидный запрос от matcher-notifier")
  public void testPositiveHandleMatcher() {
    // GIVEN
    var request = """
        {
          "request_id": "dd003e53-2700-4cf5-85af-42d5584a5c05",
          "user_id": "1000453364",
          "stu": "MA"
        }
        """;
    var mockedContext = Mockito.mock(RoutingContext.class);
    when(mockedContext.getBodyAsString()).thenReturn(request);

    var handler = new BodyParserHandler<>(MatcherRequest.class);
    // WHEN
    handler.handle(mockedContext);
    // THEN
    assertsPositiveCase(mockedContext);
  }

  @Test(description = "Проверяем кейс, когда получен не валидный запрос от matcher-notifier")
  public void testNegativeHandleMatcher() {
    // GIVEN
    var request = """
        {
          "user_id": "1000453364",
          "stu": "MA"
        }
        """;
    var mockedContext = Mockito.mock(RoutingContext.class);
    when(mockedContext.getBodyAsString()).thenReturn(request);

    var handler = new BodyParserHandler<>(MatcherRequest.class);
    // WHEN
    handler.handle(mockedContext);
    // THEN
    assertsNegativeCase(mockedContext);
  }

  @Test(dataProvider = "invalidBody",
        description = "Проверяем кейс, когда получен не валидный запрос от matcher-notifier")
  public void testNegativeHandleInvalidBody(String body, Class<?> clazz) {
    // GIVEN
    var mockedContext = Mockito.mock(RoutingContext.class);
    when(mockedContext.getBodyAsString()).thenReturn(body);

    var handler = new BodyParserHandler<>(clazz);
    // WHEN
    handler.handle(mockedContext);
    // THEN
    assertsNegativeCase(mockedContext);

  }

  // region Дополнительные методы проверок

  /**
   * Проверки вызовов при негативных кейсах.
   *
   * @param mockedContext мок {@link io.vertx.ext.web.RoutingContext}
   */
  private void assertsNegativeCase(RoutingContext mockedContext) {
    verify(mockedContext, never()).next();
    verify(mockedContext).getBodyAsString();
    verify(mockedContext).fail(argThat(
        argument -> argument instanceof BadRequestException badRequestException
                    && badRequestException.getError().equals(ErrorApiCodesEnum.BAD_REQUEST)
                    && badRequestException.getMessage().equals(Messages.WRONG_INPUT.message)));
  }

  /**
   * Проверки вызовов при позитивных кейсах.
   *
   * @param mockedContext мок {@link io.vertx.ext.web.RoutingContext}
   */
  private void assertsPositiveCase(RoutingContext mockedContext) {
    verify(mockedContext).next();
    verify(mockedContext).getBodyAsString();
    verify(mockedContext, never()).fail(any());
  }

  // endregion
}
