package ru.rtlabs.ebs.reference.receiver.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;

/**
 * Класс, который позволяет парсить конфиги в объекты.
 */
public class ConfigParser {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private ConfigParser() {
  }

  /**
   * Парсинг конфигураций из потока в объект  {@link T}.
   *
   * @param stream набор конфигураций в потоке.
   * @param clazz  класс, в который будут собраны конфигурации.
   * @param <T>    объект конфигураций модуля.
   * @return необходимый объект конфигов.
   * @throws java.io.IOException не удалось пропарсить.
   */
  public static <T> T createConfig(InputStream stream,
                                   Class<T> clazz)
      throws IOException {
    return MAPPER.readValue(stream, clazz);
  }
}
