package ru.rtlabs.ebs.reference.receiver.clients.regbio.config.utils;

import java.util.List;

/**
 * Хранит в себе основные данные о файлах и их типах для удобного доступа и парсинга.
 */
public class ContentConfig {
  public static List<PartContent> prepPayload(String imagePartName, String imageContentType,
                                              String imageName, byte[] imageData,
                                              String soundPartName, String soundContentType,
                                              String soundName, byte[] soundData){
    return List.of(
        new PartContent(imagePartName, imageContentType, imageName, imageData),
        new PartContent(soundPartName, soundContentType, soundName, soundData));
  }
}
