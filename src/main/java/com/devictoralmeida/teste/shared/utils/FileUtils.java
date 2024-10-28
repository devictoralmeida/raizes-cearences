package com.devictoralmeida.teste.shared.utils;

public class FileUtils {
  private FileUtils() {
  }

  public static String removerExtensao(String filename) {
    if (filename == null) {
      return null;
    }

    int index = filename.lastIndexOf('.');

    if (index == -1) {
      return filename;
    }

    return filename.substring(0, index).trim();
  }
}
