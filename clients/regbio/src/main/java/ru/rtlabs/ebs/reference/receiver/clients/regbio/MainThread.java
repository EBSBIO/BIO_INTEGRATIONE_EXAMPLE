package ru.rtlabs.ebs.reference.receiver.clients.regbio;

import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.clients.regbio.exception.ServerServiceException;
import ru.rtlabs.ebs.reference.receiver.clients.regbio.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.crypto.base.exceptions.CryptoException;
import ru.rtlabs.ebs.reference.receiver.launcher.utils.Arguments;
import ru.rtlabs.ebs.reference.receiver.launcher.utils.ConfigParser;
import ru.rtlabs.ebs.reference.receiver.launcher.utils.LoggerConfig;
import ru.rtlabs.ebs.reference.receiver.launcher.utils.Utils;
import ru.rtlabs.ebs.reference.receiver.clients.regbio.config.RegbioConfig;


public class MainThread {
  private static final Logger LOGGER = LoggerFactory.getLogger(MainThread.class);

  /**
   * Основная точка запуска модуля.
   */
  public static void main(String[] args)
      throws IOException, ServerServiceException, CryptoException {
    RegbioConfig config = parseArgs(args);

    RegBioDemo.startDemo(config);
  }

  /**
   * Парсинг входящих аргументов.
   */
  private static RegbioConfig parseArgs(String[] args)
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
        .createConfig(Utils.getConfigs(configPath), RegbioConfig.class);
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
