package com.devictoralmeida.teste.shared.utils;

import java.lang.reflect.Field;

public class FormatarDadosUtils {
  private FormatarDadosUtils() {
  }

  public static void aplicarTrim(Object obj) {
    if (obj == null) {
      return;
    }

    Field[] fields = obj.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (field.getType().equals(String.class)) {
        field.setAccessible(true);

        try {
          String value = (String) field.get(obj);
          if (value != null) {
            field.set(obj, value.trim());
          }
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
