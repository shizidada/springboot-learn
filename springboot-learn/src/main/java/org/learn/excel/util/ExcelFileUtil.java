package org.learn.excel.util;

import java.io.File;
import java.io.InputStream;

/**
 * @author shizi
 */
public class ExcelFileUtil {

  public static InputStream getResourcesFileInputStream(String fileName) {
    return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
  }

  public static String getPath() {
    return ExcelFileUtil.class.getResource("/").getPath();
  }

  public static File createNewFile(String pathName) {
    File file = new File(getPath() + pathName);
    if (file.exists()) {
      file.delete();
    } else {
      if (!file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
      }
    }
    return file;
  }

  public static File readFile(String pathName) {
    return new File(getPath() + pathName);
  }
}
