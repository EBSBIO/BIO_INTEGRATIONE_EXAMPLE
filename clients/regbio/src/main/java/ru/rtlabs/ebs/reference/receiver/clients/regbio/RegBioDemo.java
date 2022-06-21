package ru.rtlabs.ebs.reference.receiver.clients.regbio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoException;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoSignatureException;
import ru.rtlabs.ebs.reference.receiver.jwt.base.JwtBuilderHash;
import ru.rtlabs.ebs.reference.receiver.clients.regbio.config.RegbioConfig;
import ru.rtlabs.ebs.reference.receiver.clients.regbio.config.utils.ContentConfig;
import ru.rtlabs.ebs.reference.receiver.clients.regbio.config.utils.PartContent;


/**
 * Основной класс логики модуля.
 */
public class RegBioDemo {
  private static final Logger LOGGER = LoggerFactory.getLogger(RegBioDemo.class);
  private static final OkHttpClient CLIENT = new OkHttpClient().newBuilder()
                                                               .readTimeout(100, TimeUnit.SECONDS)
                                                               .build();
  private static final String BIO_COLLECTING = "bio_collecting";


  private static byte[] encrypt(String body, String header, RegbioConfig config, byte[] content)
      throws JsonProcessingException, CryptoException {
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> payloadJson = mapper.readValue(body, Map.class);
    Map<String, Object> headJson = mapper.readValue(header, Map.class);
    return new JwtBuilderHash(payloadJson, headJson).encryptBO(config.cryptoConfig(), content);
  }

  private static String sign(String body, String header, RegbioConfig config, byte[] content)
      throws JsonProcessingException, CryptoException {
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> payloadJson = mapper.readValue(body, Map.class);
    Map<String, Object> headJson = mapper.readValue(header, Map.class);
    return new JwtBuilderHash(payloadJson, headJson).signBO(config.cryptoConfig(), content);
  }

  private static Request prepResponce(RegbioConfig config, String payloadData, byte[] imageContent,
                                      byte[] soundContent) {
    List<PartContent> mediaFiles;
    final String baseUrl = config.baseUrl();
    final String regBioEndpoint = config.regBioEndpoint();
    String url = baseUrl;
    if (config.encrypt()) {
      url += config.regEncryptedBioEndpoint();
    } else {
      url = baseUrl + regBioEndpoint;
    }
    if (config.encrypt()) {
      mediaFiles = ContentConfig.prepPayload(config.imagePartName(), config.encImageContentType(),
                                             config.imageName(), imageContent,
                                             config.soundPartName(), config.encSoundContentType(),
                                             config.soundName(), soundContent);
    } else {
      mediaFiles = ContentConfig.prepPayload(config.imagePartName(), config.imageContentType(),
                                             config.imageName(), imageContent,
                                             config.soundPartName(), config.soundContentType(),
                                             config.soundName(), soundContent);
    }

    MultipartBody.Builder builder = new MultipartBody.Builder();
    builder.setType(MultipartBody.FORM);
    builder.addFormDataPart("params", null,
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                               payloadData));
    for (PartContent mediaFile : mediaFiles) {
      String filename = mediaFile.getFileName().orElse(null);
      builder.addFormDataPart(mediaFile.getPartName(), filename,
                              RequestBody.create(MediaType.parse(mediaFile.getContentType()),
                                                 mediaFile.getPartContent()));
    }
    RequestBody requestBody = builder.build();

    return new Request.Builder().header("Content-Type", "multipart/form-data")
                                .header("Cookie", "ebs.session=" + UUID.randomUUID()).url(url)
                                .post(requestBody).build();
  }

  public static void startDemo(RegbioConfig config) throws IOException, CryptoException {
    final var header = new String(Files.readAllBytes(Path.of(config.payloadHeaderPath())));
    final var body = new String(Files.readAllBytes(Path.of(config.payloadBodyPath())));
    byte[] imageContent = Files.readAllBytes(Path.of(config.imageName()));
    byte[] soundContent = Files.readAllBytes(Path.of(config.soundName()));
    String payloadData = null;
    String imageSign;
    String soundSign;

    if (config.useJwtFromConfig().equals(Boolean.TRUE.toString())) {
      payloadData = config.payLoad();
      if (config.encrypt()) {
        imageContent = encrypt(body, header, config, imageContent);
        soundContent = encrypt(body, header, config, soundContent);
      }
    } else {
      if (config.encrypt()) {
        imageContent = encrypt(body, header, config, imageContent);
        soundContent = encrypt(body, header, config, soundContent);

      }
      imageSign = sign(body, header, config, imageContent);
      soundSign = sign(body, header, config, soundContent);

      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object> payloadJson = mapper.readValue(body, Map.class);
      Map<String, Object> headJson = mapper.readValue(header, Map.class);
      ArrayList<Map<String, Object>> bioCollecting = (ArrayList) payloadJson.get(BIO_COLLECTING);
      Map<String, Object> photo = bioCollecting.get(0);
      Map<String, Object> sound =  bioCollecting.get(1);
      photo.put("bio_sample_signature", imageSign);
      sound.put("bio_sample_signature", soundSign);
      List<Object> bio = Arrays.asList(photo, sound);
      payloadJson.put(BIO_COLLECTING, bio);

      try {
        JwtBuilderHash builder = new JwtBuilderHash(payloadJson, headJson);
        payloadData = builder.build(config.cryptoConfig());
        LOGGER.info("Result: {}", payloadData);
      } catch (CryptoSignatureException | JsonProcessingException e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    Response response = CLIENT.newCall(
        prepResponce(config, payloadData, imageContent, soundContent)).execute();
    LOGGER.info(String.valueOf(response));
  }

}
