package ru.rtlabs.ebs.reference.receiver;

import io.vertx.core.Vertx;
import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.config.ReferenceReceiverConfig;
import ru.rtlabs.ebs.reference.receiver.exception.ServerServiceException;
import ru.rtlabs.ebs.reference.receiver.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.service.utils.Arguments;
import ru.rtlabs.ebs.reference.receiver.utils.ConfigParser;
import ru.rtlabs.ebs.reference.receiver.utils.LoggerConfig;
import ru.rtlabs.ebs.reference.receiver.utils.Utils;
import ru.rtlabs.ebs.reference.receiver.verticles.HttpVerticle;

public class ReferenceReceiverMain {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReferenceReceiverMain.class);

  /**
   * Основная функция, с нее начинается работа модуля. Точка запуска программы
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    try {
      ReferenceReceiverConfig config = parseArgs(args);
      Vertx vertx = Vertx.vertx();
      vertx.deployVerticle(new HttpVerticle(config));
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      System.exit(-1);
    }
  }

  /**
   * Парсинг входящих аргументов.
   */
  private static ReferenceReceiverConfig parseArgs(String[] args)
      throws ServerServiceException, IOException {
    Arguments parsedArgs = parseCmdLineArgs(args);
    String configPath = parsedArgs.configurationPath();
    String logConfigPath = parsedArgs.logPath();

    if (logConfigPath != null && !logConfigPath.isEmpty()) {
      LOGGER.info(Messages.SETTING_LOGGING.message);
      LoggerConfig.initLogger(logConfigPath);
      LOGGER.info(Messages.LOGGING_ENABLED.message);
    } else {
      LOGGER.error(Messages.LOGGING_PATH_INVALID.message, logConfigPath);
    }

    return ConfigParser
        .createConfig(Utils.getConfigs(configPath), ReferenceReceiverConfig.class);
  }

  protected static Arguments parseCmdLineArgs(String[] args)
      throws ServerServiceException {
    try {
      return Utils.parseArgs(args);
    } catch (ParseException e) {
      LOGGER.error(Messages.FAILED_ARGS.message, Arrays.toString(args), e.getMessage(), e);
      throw new ServerServiceException(e.getMessage(), e);
    }
  }
}
