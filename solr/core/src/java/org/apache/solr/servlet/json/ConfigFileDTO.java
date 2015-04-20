package org.apache.solr.servlet.json;

import java.io.Serializable;

/**
 * Project master thesis 2015 (Mt15)
 * <p/>
 * Created by onur kasimlar on 05.04.15.
 */
public class ConfigFileDTO implements Serializable {

  private String name;
  private String description;

  public ConfigFileDTO() {}

  public ConfigFileDTO(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
