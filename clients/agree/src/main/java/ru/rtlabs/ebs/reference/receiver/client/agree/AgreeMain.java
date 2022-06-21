package ru.rtlabs.ebs.reference.receiver.client.agree;


import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.base.exceptions.StartModuleException;
import ru.rtlabs.ebs.reference.receiver.client.agree.config.AgreeConfig;
import ru.rtlabs.ebs.reference.receiver.client.agree.handler.impl.AgreeHandler;
import ru.rtlabs.ebs.reference.receiver.client.agree.logging.Messages;
import ru.rtlabs.ebs.reference.receiver.launcher.utils.Arguments;
import ru.rtlabs.ebs.reference.receiver.launcher.utils.ConfigParser;
import ru.rtlabs.ebs.reference.receiver.launcher.utils.LoggerConfig;
import ru.rtlabs.ebs.reference.receiver.launcher.utils.Utils;

public class AgreeMain {
  private static final Logger LOGGER = LoggerFactory.getLogger(AgreeMain.class);

  /**
   * Основная функция, с нее начинается работа модуля. Точка запуска программы.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    try {
      LOGGER.info(
          ru.rtlabs.ebs.reference.receiver.client.agree.logging.Messages.START_MODULE.message);
      AgreeConfig config = parseArgs(args);

      AgreeHandler agreeHandler = new AgreeHandler(config);
      agreeHandler.startProcess();
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      System.exit(-1);
    }
  }

  /**
   * Метод парсинга аргументов командной строки.
   *
   * @param args - аргументы, переданные при запуске модуля.
   * @return - объект, содержащий переданные для работы модуля параметры.
   * @throws StartModuleException ошибка при разборе аргументов командной строки.
   * @throws IOException          ошибка при чтении конфигурации логирования или конфигурации.
   *                              входных параметров.
   */
  private static AgreeConfig parseArgs(String[] args)
      throws StartModuleException, IOException {
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
        .createConfig(Utils.getConfigs(configPath), AgreeConfig.class);
  }

  /**
   * Метод парсинга аргументов командной строки.
   *
   * @param args - аргументы, переданные при запуске модуля.
   * @return - объект, содержащий путь до файла конфигураций и путь до файла логирования.
   * @throws StartModuleException - ошибка при разборе аргументов командной строки.
   */
  protected static Arguments parseCmdLineArgs(String[] args)
      throws StartModuleException {
    try {
      return Utils.parseArgs(args);
    } catch (ParseException e) {
      LOGGER.error(Messages.FAILED_ARGS.message, Arrays.toString(args), e.getMessage(), e);
      throw new StartModuleException(e.getMessage(), e);
    }
  }
}
