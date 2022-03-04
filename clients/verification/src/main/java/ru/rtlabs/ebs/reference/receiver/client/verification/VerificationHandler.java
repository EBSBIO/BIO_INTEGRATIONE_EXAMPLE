package ru.rtlabs.ebs.reference.receiver.client.verification;

import java.io.IOException;
import java.util.UUID;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.client.verification.config.VerificationConfig;
import ru.rtlabs.ebs.reference.receiver.client.verification.exceptions.EbsRequestException;
import ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs.ICreateSession;
import ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs.IResult;
import ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs.IUploadBiometricData;
import ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs.impl.CreateSession;
import ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs.impl.Result;
import ru.rtlabs.ebs.reference.receiver.client.verification.remote.ebs.impl.UploadBiometricData;
import ru.rtlabs.ebs.reference.receiver.client.verification.remote.utils.HttpClientUtils;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoException;


public final class VerificationHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(VerificationHandler.class);
  private final VerificationConfig config;

  public VerificationHandler(VerificationConfig config) {
    this.config = config;
  }

  public void startProcess() {
    try {
      OkHttpClient client = HttpClientUtils.buildClient();
      ICreateSession createSession = new CreateSession(config, client);
      // NOTICE: данный вызов необходим только при наличии liveness, например с мнемоникой face_with_active_liveness
      //      IGetInstructions getInstructions = new GetInstructions(config, client);
      IUploadBiometricData uploadBiometricData = new UploadBiometricData(config, client);
      IResult result = new Result(config, client);

      String sessionId = UUID.randomUUID().toString().replace("-", "");
      LOGGER.info("Сгенерирован идентификатор сессии: \"{}\"", sessionId);
      String createSessionJwt = createSession.getCreateSessionJwt();

      createSession.createSession(createSessionJwt, sessionId);

      // NOTICE: данный вызов необходим только при наличии liveness, например с мнемоникой face_with_active_liveness
      //      getInstructions.getInstructions(sessionId);

      String verifyToken = uploadBiometricData.uploadBioData(sessionId);

      String resultJwt = result.getResultJwt(verifyToken);
      result.result(resultJwt, sessionId);


    } catch (CryptoException | IOException | EbsRequestException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
