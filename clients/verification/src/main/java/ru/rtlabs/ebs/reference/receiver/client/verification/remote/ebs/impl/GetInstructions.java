package ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs.impl;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.client.verification.config.VerificationConfig;
import ru.rtlabs.ebs.reference.receiver.client.verification.exceptions.EbsGetInstructionsException;
import ru.rtlabs.ebs.reference.receiver.client.verification.logging.ErrorMessages;
import ru.rtlabs.ebs.reference.receiver.client.verification.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs.IGetInstructions;


public class GetInstructions implements IGetInstructions {
  public static final String APPLICATION_JSON = "application/json";
  public static final MediaType JSON = MediaType.get(
      "%s; charset=utf-8".formatted(APPLICATION_JSON));
  public static final String CONTENT_TYPE = "Content-Type";
  private static final String CLIENT_TYPE_VALUE = "application/vnd.ebs.v1.web+json";
  public static final String AGENT
      = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.3";
  public static final String CLIENT_TYPE = "Client-Type";
  private static final Logger LOGGER = LoggerFactory.getLogger(GetInstructions.class);
  private static final String ACCEPT = "Accept";
  private final VerificationConfig config;
  private final OkHttpClient client;

  public GetInstructions(VerificationConfig config, OkHttpClient client) {
    this.config = config;
    this.client = client;
  }

  @Override
  public void getInstructions(String sessionId)
      throws IOException, EbsGetInstructionsException {

    Request request = new Request.Builder()
        .header(CONTENT_TYPE, APPLICATION_JSON)
        .header(CLIENT_TYPE, CLIENT_TYPE_VALUE)
        .header(ACCEPT, APPLICATION_JSON)
        .header("User-Agent", AGENT)
        .url("%s%s".formatted(config.ebsConfig().host(),
                              config.ebsConfig().getInstructionsUrl().formatted(sessionId)))
        .post(RequestBody.Companion.create(config.ebsConfig().getInstructions().metadata(), JSON))
        .build();

    LOGGER.info(Messages.SEND_REQUEST_GET_INSTRUCTIONS.message);
    LOGGER.info(Messages.SEND_REQUEST.message, request);

    Response response = client.newCall(request).execute();

    int code = response.code();
    ResponseBody responseBody = response.body();
    String body = responseBody == null ? "" : responseBody.string();

    LOGGER.info(Messages.REQUEST_RESULT.message, body, code);

    if (code != 200) {
      throw new EbsGetInstructionsException(
          ErrorMessages.GET_INSTRUCTIONS_FAILED.message.formatted(body, code));
    }
  }
}
