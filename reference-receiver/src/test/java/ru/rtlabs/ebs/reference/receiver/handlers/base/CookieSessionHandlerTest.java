package ru.rtlabs.ebs.reference.receiver.handlers.base;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.impl.CookieImpl;
import io.vertx.ext.web.RoutingContext;
import java.util.UUID;
import org.mockito.Mockito;
import org.testng.annotations.Test;
import ru.rtlabs.ebs.reference.receiver.exception.BadRequestException;
import ru.rtlabs.ebs.reference.receiver.exception.ErrorApiCodesEnum;
import ru.rtlabs.ebs.reference.receiver.logging.Messages;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CookieSessionHandlerTest {
  private static final String EBS_SESSION_FIELD = "ebs.session";

  @Test(
      description = "Проверяем, что если не был передан Cookie ebs.session, то "
                    + "получим ошибку http кодом 400 - неверный запрос")
  public void testNegativeHandleWithoutCookie() {
    // GIVEN
    var mockedContext = Mockito.mock(RoutingContext.class);
    var handler = new CookieSessionHandler();
    var mockRequest = mock(HttpServerRequest.class);

    when(mockedContext.request()).thenReturn(mockRequest);
    // WHEN
    handler.handle(mockedContext);
    // THEN
    verify(mockedContext, never()).next();
    verify(mockedContext).fail(argThat(
        argument -> argument instanceof BadRequestException badRequestException
                    && badRequestException.getError().equals(ErrorApiCodesEnum.BAD_REQUEST)
                    && badRequestException.getMessage()
                                          .equals(
                                              Messages.ERROR_COOKIE_EBS_SESSION_NOT_FOUND.message)));
  }

  @Test(
      description = "Проверяем, что если был передан Cookie ebs.session")
  public void testPositiveHandleWithCookie() {
    // GIVEN
    var mockedContext = Mockito.mock(RoutingContext.class);
    var mockRequest = mock(HttpServerRequest.class);
    String requestId = UUID.randomUUID().toString();
    when(mockedContext.request()).thenReturn(mockRequest);
    when(mockedContext.request().getCookie(EBS_SESSION_FIELD))
        .thenReturn(new CookieImpl(EBS_SESSION_FIELD, requestId));

    var handler = new CookieSessionHandler();
    // WHEN
    handler.handle(mockedContext);
    // THEN
    verify(mockedContext).next();
    verify(mockedContext, never()).fail(any());
  }
}
