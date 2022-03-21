package ru.rtlabs.ebs.reference.receiver.utils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.LoggerFactory;

public class LoggerConfig {

  private LoggerConfig() {
  }

  public static void initLogger(String configFilePath) throws IOException {
    try (InputStream configStream = new FileInputStream(configFilePath)) {
      LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
      loggerContext.reset();
      JoranConfigurator configurator = new JoranConfigurator();
      configurator.setContext(loggerContext);
      configurator.doConfigure(configStream); // loads logback file
    } catch (JoranException e) {
      throw new IOException("Ошибка при чтении конфигурации логирования", e);
    }
  }
}
