package ru.rtlabs.ebs.reference.receiver.handlers.base;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;
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

public class CheckAuthHeaderHandlerTest {
  private static final String BEARER_VALUE = "something_token";

  @Test(
      description = "Проверяем, что если не был передан Cookie ebs.session, то "
                    + "получим ошибку c http кодом 400 - неверный запрос")
  public void testNegativeHandleWithoutAuth() {
    // GIVEN
    var mockedContext = Mockito.mock(RoutingContext.class);
    var handler = new CheckAuthHeaderHandler(BEARER_VALUE);
    var mockRequest = mock(HttpServerRequest.class);

    when(mockedContext.request()).thenReturn(mockRequest);
    // WHEN
    handler.handle(mockedContext);
    // THEN
    verify(mockedContext, never()).next();
    verify(mockedContext).fail(argThat(
        argument -> argument instanceof BadRequestException badRequestException
                    && badRequestException.getError().equals(ErrorApiCodesEnum.UNAUTHORIZED)
                    && badRequestException.getMessage().equals(Messages.UNAUTHORIZED.message)));
  }


  @Test(
      description = "Проверяем, что если был передан Cookie ebs.session")
  public void testPositiveHandleWithAuth() {
    // GIVEN
    var mockedContext = Mockito.mock(RoutingContext.class);
    var mockRequest = mock(HttpServerRequest.class);
    String requestId = UUID.randomUUID().toString();
    when(mockedContext.request()).thenReturn(mockRequest);
    when(mockedContext.request().getHeader(HttpHeaders.AUTHORIZATION))
        .thenReturn("Bearer %s".formatted(BEARER_VALUE));

    var handler = new CheckAuthHeaderHandler(BEARER_VALUE);
    // WHEN
    handler.handle(mockedContext);
    // THEN
    verify(mockedContext).next();
    verify(mockedContext, never()).fail(any());
  }
}
