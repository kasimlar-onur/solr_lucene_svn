package org.apache.solr.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Project master thesis 2015 (Mt15)
 * <p/>
 * Created by onur kasimlar on 01.04.15.
 *
 *
 * This class reads the properties from mt15settings.properties.
 *
 */
public class MT15PropertyReader {

  private Map<String, String> propertiesMap = null;

  public MT15PropertyReader() {
    initPropertiesMap();
  }

  private void initPropertiesMap() {
    propertiesMap = new HashMap<String, String>();
    Properties prop = new Properties();
    try {
      //load a properties file from class path, inside static method

      File file = new File("conf/mt15settings.properties");
      FileInputStream fileInput = new FileInputStream(file);
      prop.load(fileInput);
      //get the property value and print it out
      propertiesMap.put("mt15.settings.folder.path", prop.getProperty("mt15.settings.folder.path"));

    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public Map<String, String> getPropertiesMap() {
    if (propertiesMap == null) initPropertiesMap();
    return propertiesMap;
  }
}
