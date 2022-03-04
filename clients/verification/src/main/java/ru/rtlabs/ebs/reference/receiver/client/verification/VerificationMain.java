package ru.rtlabs.ebs.reference.receiver.client.verification;

import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.base.exceptions.StartModuleException;
import ru.rtlabs.ebs.reference.receiver.client.verification.config.VerificationConfig;
import ru.rtlabs.ebs.reference.receiver.client.verification.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.launcher.utils.Arguments;
import ru.rtlabs.ebs.reference.receiver.launcher.utils.ConfigParser;
import ru.rtlabs.ebs.reference.receiver.launcher.utils.LoggerConfig;
import ru.rtlabs.ebs.reference.receiver.launcher.utils.Utils;

public final class VerificationMain {

  private static final Logger LOGGER = LoggerFactory.getLogger(VerificationMain.class);

  /**
   * Основная функция, с нее начинается работа модуля. Точка запуска программы
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    try {
      VerificationConfig config = parseArgs(args);
      VerificationHandler handler = new VerificationHandler(config);

      handler.startProcess();
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      System.exit(-1);
    }
  }

  /**
   * Парсинг входящих аргументов.
   */
  private static VerificationConfig parseArgs(String[] args)
      throws IOException, StartModuleException {
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
        .createConfig(Utils.getConfigs(configPath), VerificationConfig.class);
  }

  private static Arguments parseCmdLineArgs(String[] args)
      throws StartModuleException {
    try {
      return Utils.parseArgs(args);
    } catch (ParseException e) {
      LOGGER.error(Messages.FAILED_ARGS.message, Arrays.toString(args), e.getMessage(), e);
      throw new StartModuleException(e.getMessage(), e);
    }
  }
}
