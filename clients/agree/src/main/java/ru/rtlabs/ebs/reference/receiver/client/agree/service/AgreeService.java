package ru.rtlabs.ebs.reference.receiver.client.agree.service;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.client.agree.logging.Messages;

public class AgreeService implements IAgreeService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AgreeService.class);

  /**
   * Отправка запроса.
   *
   * @param request Отправляемый запрос.
   * @return Полученный ответ.
   */
  private Response send(Request request) {
    OkHttpClient client = new OkHttpClient();
    try {
      return client.newCall(request).execute();
    } catch (IOException e) {
      LOGGER.error(Messages.SEND_REQUEST_ERROR.message, request.url(), e.getMessage());
      return null;
    }
  }

  /**
   * Отправка запроса на agree.
   *
   * @param url адрес, куда отправлять запрос.
   * @param jwt сформированная jwt для отправки в запросе.
   */
  @Override
  public void sendRegistration(URL url, String jwt) {
    Request request = prepareRequest(jwt, url);
    LOGGER.info(Messages.CREATE_REQUEST_FOR_AGREE.message, request);

    Response response = send(request);
    if (response != null) {
      String responseBody = "";

      try {
        responseBody = Objects.requireNonNull(response.body()).string();
      } catch (NullPointerException | IOException ioException) {
        LOGGER.error(Messages.EMPTY_RESPONSE_BODY.message);
      }
      LOGGER.info(Messages.GET_RESPONSE.message, response, responseBody);
    } else {
      LOGGER.error(Messages.EMPTY_RESPONSE.message);
    }
  }

  /**
   * Формирование запроса для отправки в agree.
   *
   * @param url    адрес, куда отправлять запрос.
   * @param jwtStr сформированная jwt для отправки в запросе.
   * @return готовый для отправки запрос.
   */
  private Request prepareRequest(String jwtStr, URL url) {

    MultipartBody.Builder builder = new MultipartBody.Builder();
    builder.setType(MultipartBody.FORM);
    builder.addFormDataPart("params", null, RequestBody
        .create(jwtStr, MediaType.parse("application/octet-stream")));
    RequestBody requestBody = builder.build();

    return new Request.Builder()
        .header("Content-Type", "multipart/form-data")
        .url(Objects.requireNonNull(url))
        .post(requestBody).build();
  }
}
