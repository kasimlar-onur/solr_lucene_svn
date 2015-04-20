package org.apache.solr.servlet.json;

import java.io.Serializable;
import java.util.List;

/**
 * Project master thesis 2015 (Mt15)
 * <p/>
 * Created by onur kasimlar on 07.01.15.
 */
public class SearchHandlerDTO implements Serializable {

  private String searchHandlerName;
  private List<String> searchComponents;
  private List<String> defaultComponents;
  private List<String> firstComponents;
  private List<String> lastComponents;

  public SearchHandlerDTO(String searchHandlerName, List<String> searchComponents, List<String> defaultComponents,
                          List<String> firstComponents, List<String> lastComponents) {
    this.searchHandlerName = searchHandlerName;
    this.searchComponents = searchComponents;
    this.defaultComponents = defaultComponents;
    this.firstComponents = firstComponents;
    this.lastComponents = lastComponents;
  }

  public String getSearchHandlerName() {
    return searchHandlerName;
  }

  public void setSearchHandlerName(String searchHandlerName) {
    this.searchHandlerName = searchHandlerName;
  }

  public List<String> getSearchComponents() {
    return searchComponents;
  }

  public void setSearchComponents(List<String> searchComponents) {
    this.searchComponents = searchComponents;
  }

  public List<String> getDefaultComponents() {
    return defaultComponents;
  }

  public void setDefaultComponents(List<String> defaultComponents) {
    this.defaultComponents = defaultComponents;
  }

  public List<String> getLastComponents() {
    return lastComponents;
  }

  public void setLastComponents(List<String> lastComponents) {
    this.lastComponents = lastComponents;
  }

  public List<String> getFirstComponents() {
    return firstComponents;
  }

  public void setFirstComponents(List<String> firstComponents) {
    this.firstComponents = firstComponents;
  }
}
