package ru.rtlabs.ebs.reference.receiver.service.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import java.util.List;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.rtlabs.ebs.reference.receiver.exception.BadRequestException;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.BaseJwt;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload.HeaderUploadJwt;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload.PayloadUploadJwt;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload.Vectors;
import ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload.VectorsSign;

import static org.testng.Assert.assertEquals;

public class ValidatorUploadTest {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private static final String ALG_VALUE = "RS256";
  private static final String TYP_VALUE = "JWT";
  private static final String SUB_VALUE = "extID1";
  private static final String AUD_VALUE = "test_aud";
  private static final VectorsSign VECTORS_SIGN_VALUE = new VectorsSign("alg_value",
                                                                        "data_value".getBytes(),
                                                                        "keyId_value", "typ_value");
  private static final Vectors VECTOR_VALUE = new Vectors("modality_value", VECTORS_SIGN_VALUE,
                                                          "vendor_name_value");

  private static final List<Vectors> VECTORS_VALUE = List.of(VECTOR_VALUE);
  private static final Long EXP_VALUE = 1644333905L;
  private static final Long IAT_VALUE = 1644333905L;


  @Test
  public void testPositiveValidation() throws BadRequestException, JsonProcessingException {
    // GIVEN
    ValidatorUpload upload = new ValidatorUpload();

    String header = buildHeader(ALG_VALUE, TYP_VALUE);
    String payload = buildPayload(SUB_VALUE, AUD_VALUE, IAT_VALUE, EXP_VALUE, VECTORS_VALUE);

    BaseJwt<HeaderUploadJwt, PayloadUploadJwt> expectedResult = new BaseJwt<>(
        OBJECT_MAPPER.readValue(header, HeaderUploadJwt.class),
        OBJECT_MAPPER.readValue(payload, PayloadUploadJwt.class));
    // WHEN
    BaseJwt<HeaderUploadJwt, PayloadUploadJwt> actualResult = upload.parserJwt(
        buildJwt(header, payload));
    // THEN
    assertEquals(expectedResult, actualResult);
  }


  @DataProvider(name = "invalidJwt")
  private static Object[][] invalidJwtProvider() throws JsonProcessingException {
    return new Object[][] {
        {buildHeader(null, TYP_VALUE),
         buildPayload(SUB_VALUE, AUD_VALUE, IAT_VALUE, EXP_VALUE, VECTORS_VALUE)},
        {buildHeader("", TYP_VALUE),
         buildPayload(SUB_VALUE, AUD_VALUE, IAT_VALUE, EXP_VALUE, VECTORS_VALUE)},
        {buildHeader(ALG_VALUE, null),
         buildPayload(SUB_VALUE, AUD_VALUE, IAT_VALUE, EXP_VALUE, VECTORS_VALUE)},
        {buildHeader(ALG_VALUE, ""),
         buildPayload(SUB_VALUE, AUD_VALUE, IAT_VALUE, EXP_VALUE, VECTORS_VALUE)},
        {buildHeader(ALG_VALUE, TYP_VALUE),
         buildPayload(null, AUD_VALUE, IAT_VALUE, EXP_VALUE, VECTORS_VALUE)},
        {buildHeader(ALG_VALUE, TYP_VALUE),
         buildPayload("", AUD_VALUE, IAT_VALUE, EXP_VALUE, VECTORS_VALUE)},
        {buildHeader(ALG_VALUE, TYP_VALUE),
         buildPayload(SUB_VALUE, null, IAT_VALUE, EXP_VALUE, VECTORS_VALUE)},
        {buildHeader(ALG_VALUE, TYP_VALUE),
         buildPayload(SUB_VALUE, "", IAT_VALUE, EXP_VALUE, VECTORS_VALUE)},
        {buildHeader(ALG_VALUE, TYP_VALUE),
         buildPayload(SUB_VALUE, AUD_VALUE, null, EXP_VALUE, VECTORS_VALUE)},
        {buildHeader(ALG_VALUE, TYP_VALUE),
         buildPayload(SUB_VALUE, AUD_VALUE, IAT_VALUE, null, VECTORS_VALUE)},
        {buildHeader(ALG_VALUE, TYP_VALUE),
         buildPayload(SUB_VALUE, AUD_VALUE, IAT_VALUE, null, List.of())},
        {buildHeader(ALG_VALUE, TYP_VALUE),
         buildPayload(SUB_VALUE, AUD_VALUE, IAT_VALUE, null, null)},
        };
  }

  @Test(dataProvider = "invalidJwt", expectedExceptions = BadRequestException.class)
  public void testNegativeValidation(String header, String payload) throws BadRequestException {
    // GIVEN
    ValidatorUpload upload = new ValidatorUpload();

    // WHEN
    upload.parserJwt(buildJwt(header, payload));
    // THEN ожидаем ошибку
  }

  // region Вспомогательные методы
  private String buildJwt(String header, String payload) {
    return String.format("%s.%s.sign", Base64.getEncoder().encodeToString(header.getBytes()),
                         Base64.getEncoder().encodeToString(payload.getBytes()));
  }

  private static String buildHeader(String alg, String typ) {
    StringBuilder builder = new StringBuilder("{");
    String prefix = "";
    if (alg != null) {
      builder.append(String.format("\"alg\": \"%s\"", alg));
      prefix = ", ";
    }
    if (typ != null) {
      builder.append(prefix).append(String.format("\"typ\": \"%s\"", typ));
    }
    return builder.append("}").toString();
  }

  private static String buildPayload(String sub, String aud, Long iat, Long exp,
                                     List<Vectors> vectors) throws JsonProcessingException {
    StringBuilder builder = new StringBuilder("{");
    String prefix = "";

    if (sub != null) {
      builder.append(String.format("\"sub\": \"%s\"", sub));
      prefix = ", ";
    }
    if (aud != null) {
      builder.append(prefix).append(String.format("\"aud\": \"%s\"", aud));
      prefix = ", ";
    }
    if (iat != null) {
      builder.append(prefix).append(String.format("\"iat\": %s", iat));
      prefix = ", ";
    }
    if (exp != null) {
      builder.append(prefix).append(String.format("\"exp\": %s", exp));
    }
    if (vectors != null) {
      builder.append(prefix)
             .append(String.format("\"vectors\": %s", OBJECT_MAPPER.writeValueAsString(vectors)));
    }
    return builder.append("}").toString();
  }
  // endregion

}
