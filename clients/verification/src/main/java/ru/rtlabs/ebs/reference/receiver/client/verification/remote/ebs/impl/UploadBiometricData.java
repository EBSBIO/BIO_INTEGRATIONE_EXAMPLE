package ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.client.verification.config.VerificationConfig;
import ru.rtlabs.ebs.reference.receiver.client.verification.exceptions.EbsUploadException;
import ru.rtlabs.ebs.reference.receiver.client.verification.logging.ErrorMessages;
import ru.rtlabs.ebs.reference.receiver.client.verification.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs.IUploadBiometricData;


public class UploadBiometricData implements IUploadBiometricData {
  public static final String APPLICATION_JSON = "application/json";
  public static final MediaType JSON = MediaType.get(
      "%s; charset=utf-8".formatted(APPLICATION_JSON));
  public static final String CLIENT_TYPE = "Client-Type";
  private static final String ACCEPT = "application/vnd.ebs.v1.web+json";
  private static final String VERIFICATION_TOKEN = "verification-token";
  private static final Logger LOGGER = LoggerFactory.getLogger(UploadBiometricData.class);
  private final VerificationConfig config;
  private final OkHttpClient client;

  public UploadBiometricData(VerificationConfig config, OkHttpClient client) {
    this.config = config;
    this.client = client;
  }

  @Override
  public String uploadBioData(String sessionId)
      throws IOException, EbsUploadException {
    Request request = new Request.Builder()
        .header(CLIENT_TYPE, ACCEPT)
        .url("%s%s".formatted(config.ebsConfig().host(),
                              config.ebsConfig().uploadUrl().formatted(sessionId)))
        .post(buildMultipart())
        .build();

    LOGGER.info(Messages.SEND_REQUEST_UPLOAD.message);
    LOGGER.info(Messages.SEND_REQUEST.message, request);

    Response response = client.newCall(request).execute();

    int code = response.code();
    ResponseBody responseBody = response.body();
    String body = responseBody == null ? "" : responseBody.string();

    LOGGER.info(Messages.REQUEST_RESULT.message, body, code);

    if (code != 200) {
      throw new EbsUploadException(ErrorMessages.VERIFICATION_FAILED.message.formatted(body,
                                                                                       code));
    }

    String verificationToken = response.header(VERIFICATION_TOKEN);

    if (verificationToken == null || verificationToken.isBlank()) {
      throw new EbsUploadException(ErrorMessages.VERIFICATION_TOKEN_GET_FAILED.message);
    }
    LOGGER.info(Messages.VERIFICATION_TOKEN_GET_SUCCESS.message, verificationToken);

    return verificationToken;
  }

  /**
   * Метод формирования мультипарта для выполнения запроса на верификацию. В данном примере
   * рассматривается работа *без* активного лайвнеса, а так же только мнемоника photo.
   *
   * @return собранный мультипарт для выполнения запроса.
   * @throws IOException ошибка при получении файла.
   */
  private MultipartBody buildMultipart() throws IOException {
    MultipartBody.Builder builder = new MultipartBody.Builder();

    // NOTICE: добавляем в мультипарт БО, название парта "bs_{modality}". В данном примере рассматривается работа только с фото.
    if (config.ebsConfig().upload().photoData() != null) {
      File file = new File(config.ebsConfig().upload().photoData().path());
      builder.addFormDataPart("bs_photo",
                              config.ebsConfig().upload().photoData().fileName(),
                              RequestBody.create(Files.readAllBytes(file.toPath()),
                                                 MediaType.parse(
                                                     config.ebsConfig().upload().photoData()
                                                           .contentType())));
    }
    // NOTICE: в данный мультипарт необходимо добавить инструкции, которые были выданы на предыдущем этапе,
    // с указанием времени выполнения 'client_duration', если был активный liveness
    builder.addFormDataPart("metadata", null,
                            RequestBody.create(config.ebsConfig().upload().metadata(), JSON));

    builder.setType(MultipartBody.FORM);
    MultipartBody requestBody = builder.build();
    LOGGER.info(Messages.MULTIPART_COUNT.message, requestBody.parts().size());
    int i = 0;
    for (MultipartBody.Part part : requestBody.parts()) {
      LOGGER.info("{}: {} - {}", i, part.body().contentType(), part.body().contentLength());
      i++;
    }

    return requestBody;
  }
}
