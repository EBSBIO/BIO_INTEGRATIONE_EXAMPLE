package ru.rtlabs.ebs.reference.receiver.handlers;

import io.vertx.ext.web.RoutingContext;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.rtlabs.ebs.reference.receiver.service.utils.ContextData;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SaveJWTHandlerTest {
  @DataProvider(name = "dataBodyProvider")
  private Object[] dataBodyProvider() {
    return new Object[] {
        null,
        "",
        "something_value",
        };
  }

  @Test(dataProvider = "dataBodyProvider")
  public void testPositiveHandle(String dataBody) {
    // GIVEN
    var mockedContext = Mockito.mock(RoutingContext.class);
    when(mockedContext.getBodyAsString()).thenReturn(dataBody);

    var handler = new SaveJWTHandler();
    // WHEN
    handler.handle(mockedContext);
    // THEN
    verify(mockedContext).next();
    verify(mockedContext).put(ContextData.JWT_TOKEN.key, dataBody);
    verify(mockedContext, never()).fail(any());
  }
}
