package ru.rtlabs.ebs.reference.receiver.handlers;

import io.vertx.ext.web.RoutingContext;
import java.util.List;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.rtlabs.ebs.reference.receiver.exception.BadRequestException;
import ru.rtlabs.ebs.reference.receiver.exception.ErrorApiCodesEnum;
import ru.rtlabs.ebs.reference.receiver.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.marshalling.MultipartData;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.BaseJwt;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload.HeaderUploadJwt;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload.PayloadUploadJwt;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload.Vectors;
import ru.rtlabs.ebs.reference.receiver.service.utils.ContextData;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class BioskudRouterValidateVectorsHandlerTest {
  private static final String VENDOR_NAME = "stub_photo_1.0.0";
  private PayloadUploadJwt payloadUploadJwt;

  @BeforeClass
  public void setUp() {
    Vectors vectors = Mockito.mock(Vectors.class);
    when(vectors.vendor()).thenReturn(VENDOR_NAME);
    payloadUploadJwt = mock(PayloadUploadJwt.class);
    when(payloadUploadJwt.vectors()).thenReturn(List.of(vectors));

  }

  @Test
  public void testPositiveHandler() {
    // GIVEN
    HeaderUploadJwt headerUploadJwt = Mockito.mock(HeaderUploadJwt.class);

    MultipartData multipartData = mock(MultipartData.class);
    when(multipartData.name()).thenReturn(VENDOR_NAME);

    var mockedContext = Mockito.mock(RoutingContext.class);
    when(mockedContext.get(ContextData.JWT_OBJECT.key)).thenReturn(
        new BaseJwt<>(headerUploadJwt, payloadUploadJwt));
    when(mockedContext.get(ContextData.DATA.key)).thenReturn(List.of(multipartData));

    var handler = new BioskudRouterValidateVectorsHandler();
    // WHEN
    handler.handle(mockedContext);
    // THEN
    verify(mockedContext).next();
    verify(mockedContext).get(ContextData.JWT_OBJECT.key);
    verify(mockedContext).get(ContextData.DATA.key);
    verify(mockedContext, never()).fail(any());
  }

  @Test(
      description = "Проверяем кейс, когда в multipart не переданы данные вендора, которые были в jwt")
  public void testNegativeHandlerNotFountVendor() {
    // GIVEN
    HeaderUploadJwt headerUploadJwt = Mockito.mock(HeaderUploadJwt.class);

    var vendorNameMultipart = "something_name";

    MultipartData multipartData = mock(MultipartData.class);
    when(multipartData.name()).thenReturn(vendorNameMultipart);

    var mockedContext = Mockito.mock(RoutingContext.class);
    when(mockedContext.get(ContextData.JWT_OBJECT.key)).thenReturn(
        new BaseJwt<>(headerUploadJwt, payloadUploadJwt));
    when(mockedContext.get(ContextData.DATA.key)).thenReturn(List.of(multipartData));

    var handler = new BioskudRouterValidateVectorsHandler();
    try {
      // WHEN
      handler.handle(mockedContext);
    } catch (Exception e) {
      // THEN
      verify(mockedContext, never()).next();
      verify(mockedContext).get(ContextData.JWT_OBJECT.key);
      verify(mockedContext).get(ContextData.DATA.key);
      if (e instanceof BadRequestException badRequestException) {
        assertEquals(badRequestException.getError(), ErrorApiCodesEnum.BAD_REQUEST);
        assertEquals(badRequestException.getMessage(), Messages.ERROR_PARSE_REQUEST.message);
      } else {
        fail("Получена неожиданная ошибка");
      }
    }
  }
}
