package ru.rtlabs.ebs.reference.receiver.clients.regbio.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rtlabs.ebs.reference.receiver.configurations.crypto.CryptoConfig;

/**
 * Конфигурация сервиса.
 *
 * @param payLoad                 JWT токен хранящий в себе meta и подпись.
 * @param regBioEndpoint          Endpoint для обращения к модулю regbio с незашифрованными БО.
 * @param encrypt                 Поле отвечающее за сценарий. Если true, то шифруем БО и отправляем
 *                                на API {@link RegbioConfig#regEncryptedBioEndpoint}, в ином случае,
 *                                не шифруем и отправляем на @param regBioEndpoint.
 * @param useJwtFromConfig        Если true, то берем payLoad из конфига, иначе делаем свой.
 * @param imagePartName           Наименование "парта" фотографии.
 * @param soundPartName           Наименование "парта" звука.
 * @param soundName               Путь до аудио файла в контейнере.
 * @param imageName               Путь до фото файла в контейнере.
 * @param payloadBodyPath         Путь до файла body.json.
 * @param payloadHeaderPath       Путь до файла head.json.
 * @param cryptoConfig            Часть properties.json файла отвечающая за криптографию.
 * @param regEncryptedBioEndpoint Endpoint для обращения к модулю regbio с зашифрованными БО.
 * @param baseUrl                 Базовый URL до сервисов ebs.
 */

public record RegbioConfig(
    @JsonProperty(value = PAYLOAD, required = true) String payLoad,
    @JsonProperty(value = API_ENDPOINT, required = true) String regBioEndpoint,
    @JsonProperty(value = ENCRYPT, required = true) boolean encrypt,
    @JsonProperty(value = USE_JWT_FROM_CONFIG, required = true) String useJwtFromConfig,
    @JsonProperty(value = IMAGE_PN, required = true) String imagePartName,
    @JsonProperty(value = SOUND_PN, required = true) String soundPartName,
    @JsonProperty(value = SOUND_NAME, required = true) String soundName,
    @JsonProperty(value = IMAGE_NAME, required = true) String imageName,
    @JsonProperty(value = BODY_PATH, required = true) String payloadBodyPath,
    @JsonProperty(value = HEADER_PATH, required = true) String payloadHeaderPath,
    @JsonProperty(value = CRYPTO_CONFIG, required = true) CryptoConfig cryptoConfig,
    @JsonProperty(value = ENCRYPT_API_ENDPOINT, required = true) String regEncryptedBioEndpoint,
    @JsonProperty(value = IMAGE_CONTENT_TYPE, required = true) String imageContentType,
    @JsonProperty(value = SOUND_CONTENT_TYPE, required = true) String soundContentType,
    @JsonProperty(value = ENC_IMAGE_CONTENT_TYPE, required = true) String encImageContentType,
    @JsonProperty(value = ENC_SOUND_CONTENT_TYPE, required = true) String encSoundContentType,
    @JsonProperty(value = BASE_URL, required = true) String baseUrl) {
  private static final String PAYLOAD = "payload";
  private static final String API_ENDPOINT = "reg_bio_endpoint";
  private static final String BASE_URL = "base_url";
  private static final String ENCRYPT = "encrypt";
  private static final String USE_JWT_FROM_CONFIG = "use_jwt_from_config";
  private static final String ENCRYPT_API_ENDPOINT = "reg_encrypted_bio_endpoint";
  private static final String IMAGE_NAME = "image_name";
  private static final String SOUND_NAME = "sound_name";
  private static final String SOUND_PN = "sound_part_name";
  private static final String IMAGE_PN = "image_part_name";
  private static final String BODY_PATH = "payload_body_path";
  private static final String HEADER_PATH = "payload_header_path";
  private static final String IMAGE_CONTENT_TYPE = "image_content_type";
  private static final String SOUND_CONTENT_TYPE = "sound_content_type";
  private static final String ENC_IMAGE_CONTENT_TYPE = "enc_image_content_type";
  private static final String ENC_SOUND_CONTENT_TYPE = "enc_sound_content_type";
  private static final String CRYPTO_CONFIG = "crypto_config";
}
