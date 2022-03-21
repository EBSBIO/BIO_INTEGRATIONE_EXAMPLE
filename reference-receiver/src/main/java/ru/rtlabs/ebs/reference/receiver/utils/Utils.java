package ru.rtlabs.ebs.reference.receiver.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rtlabs.ebs.reference.receiver.service.utils.Arguments;

public final class Utils {

  private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

  private static final String OPT_LOG = "l";
  private static final String OPT_PROPERTIES = "c";
  private static final String LONG_OPT_LOG = "logproperties";
  private static final String LONG_OPT_PROPERTIES = "configuration";

  private static final String DESCRIPTION_LOG_OPT = "path to file logback.xml";
  private static final String DESCRIPTION_PROPERTIES_OPT = "path to file properties.json";

  private static final Boolean HASARG = true;

  private Utils() {

  }

  /**
   * Функция возвращает InputStream файла, путь до которого передан, как аргумент функции.
   *
   * @param path путь до файла.
   * @return FileInputStream.
   */
  public static InputStream getConfigs(String path) {
    LOGGER.info("Получение конфигурации");
    try {
      File initialFile = new File(path);
      return FileUtils.openInputStream(initialFile);
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }
    return null;
  }

  /**
   * Функция для парсинга аргуметов командной строки. Возвращает пару {@link ru.rtlabs.ebs.reference.receiver.service.utils.Arguments}, содержащую
   * пути до файла конфигурации и файла настроек для log4j.
   *
   * @param args список аргументов.
   * @return пара, содержащая пути до файла конфигурации и файла настроек для log4j.
   */
  public static Arguments parseArgs(String[] args) throws ParseException {
    Options options = new Options();

    Option log = new Option(OPT_LOG, LONG_OPT_LOG, HASARG, DESCRIPTION_LOG_OPT);
    log.setRequired(false);
    options.addOption(log);

    Option input = new Option(OPT_PROPERTIES, LONG_OPT_PROPERTIES, HASARG,
                              DESCRIPTION_PROPERTIES_OPT);
    input.setRequired(true);
    options.addOption(input);

    CommandLineParser parser = new DefaultParser();

    CommandLine cmd = parser.parse(options, args);
    return new Arguments(cmd.getOptionValue(LONG_OPT_PROPERTIES), cmd.getOptionValue(LONG_OPT_LOG));
  }
}
