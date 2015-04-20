package org.apache.solr.servlet.json;

import java.io.Serializable;
import java.util.List;

/**
 * Project master thesis 2015 (Mt15)
 * <p/>
 * Created by onur kasimlar on 12.01.15.
 */
public class ResponseDTO implements Serializable {

  private List<SearchHandlerDTO> searchHandlersDTO;
  private UpdateHandlerDTO updateHandlerDTO;
  private String solrConfigContent;

  public ResponseDTO() { }

  public ResponseDTO(List<SearchHandlerDTO> searchHandlersDTO, UpdateHandlerDTO updateHandlerDTO, String solrConfigContent) {
    this.searchHandlersDTO = searchHandlersDTO;
    this.updateHandlerDTO = updateHandlerDTO;
    this.solrConfigContent = solrConfigContent;
  }

  public List<SearchHandlerDTO> getSearchHandlersDTO() {
    return searchHandlersDTO;
  }

  public void setSearchHandlersDTO(List<SearchHandlerDTO> searchHandlersDTO) {
    this.searchHandlersDTO = searchHandlersDTO;
  }

  public UpdateHandlerDTO getUpdateHandlerDTO() {
    return updateHandlerDTO;
  }

  public void setUpdateHandlerDTO(UpdateHandlerDTO updateHandlerDTO) {
    this.updateHandlerDTO = updateHandlerDTO;
  }

  public String getSolrConfigContent() {
    return solrConfigContent;
  }

  public void setSolrConfigContent(String solrConfigContent) {
    this.solrConfigContent = solrConfigContent;
  }

}
