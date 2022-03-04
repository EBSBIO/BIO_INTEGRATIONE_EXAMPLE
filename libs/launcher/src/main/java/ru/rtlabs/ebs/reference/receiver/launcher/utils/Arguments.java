package ru.rtlabs.ebs.reference.receiver.launcher.utils;

/**
 * Входящие аргументы при запуске программы.
 *
 * @param configurationPath путь до файла конфигураций.
 * @param logPath           путь до файла логирования.
 */
public record Arguments(String configurationPath,
                        String logPath) {
}
