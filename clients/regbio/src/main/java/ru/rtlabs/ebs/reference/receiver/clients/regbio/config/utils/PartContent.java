package ru.rtlabs.ebs.reference.receiver.clients.regbio.config.utils;

import java.util.Objects;
import java.util.Optional;

/**
 * Содержит в себе информация о частях payload (фото, аудио).
 */

public final class PartContent {
  private final String partName;
  private final byte[] partContent;
  private final String contentType;
  private final String fileName;

  public PartContent(String partName, String contentType, String fileName, byte[] partContent) {
    this.partName = partName;
    this.partContent = partContent;
    this.contentType = contentType;
    this.fileName = fileName;
  }

  @Override
  public String toString() {
    return "PartContent{" + "partName='" + partName + '\'' + ", partContent length="
           + partContent.length + ", contentType='" + contentType + '\'' + ", fileName='" + fileName
           + '\'' + '}';
  }

  public String getPartName() {
    return partName;
  }

  public byte[] getPartContent() {
    return partContent.clone();
  }

  public String getContentType() {
    return contentType;
  }

  public Optional<String> getFileName() {
    return Optional.ofNullable(fileName);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PartContent that = (PartContent) o;
    return partName.equals(that.partName) && contentType.equals(that.contentType) && Objects.equals(
        fileName, that.fileName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(partName, contentType, fileName);
  }
}

