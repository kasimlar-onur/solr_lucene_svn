package org.apache.solr.servlet.json;

import java.io.Serializable;

/**
 * Project master thesis 2015 (Mt15)
 * <p/>
 * Created by onur kasimlar on 11.01.15.
 */
public class UpdateHandlerDTO implements Serializable {

  private int autoCommitMaxDocs;
  private int autoCommitMaxTime;
  private int autoSoftCommitMaxDocs;
  private int autoSoftCommitMaxTime;
  private boolean openSearcher;
  private String className;
  private int commitIntervalLowerBound;
  private boolean commitWithinSoftCommit;
  private boolean indexWriterCloseWaitsForMerges;

  public UpdateHandlerDTO() {}

  public UpdateHandlerDTO(int autoCommitMaxDocs, int autoCommitMaxTime, int autoSoftCommitMaxDocs, int autoSoftCommitMaxTime, boolean openSearcher, String className, int commitIntervalLowerBound, boolean commitWithinSoftCommit, boolean indexWriterCloseWaitsForMerges) {
    this.autoCommitMaxDocs = autoCommitMaxDocs;
    this.autoCommitMaxTime = autoCommitMaxTime;
    this.autoSoftCommitMaxDocs = autoSoftCommitMaxDocs;
    this.autoSoftCommitMaxTime = autoSoftCommitMaxTime;
    this.openSearcher = openSearcher;
    this.className = className;
    this.commitIntervalLowerBound = commitIntervalLowerBound;
    this.commitWithinSoftCommit = commitWithinSoftCommit;
    this.indexWriterCloseWaitsForMerges = indexWriterCloseWaitsForMerges;
  }

  public int getAutoCommitMaxDocs() {
    return autoCommitMaxDocs;
  }

  public void setAutoCommitMaxDocs(int autoCommitMaxDocs) {
    this.autoCommitMaxDocs = autoCommitMaxDocs;
  }

  public int getAutoCommitMaxTime() {
    return autoCommitMaxTime;
  }

  public void setAutoCommitMaxTime(int autoCommitMaxTime) {
    this.autoCommitMaxTime = autoCommitMaxTime;
  }

  public int getAutoSoftCommitMaxDocs() {
    return autoSoftCommitMaxDocs;
  }

  public void setAutoSoftCommitMaxDocs(int autoSoftCommitMaxDocs) {
    this.autoSoftCommitMaxDocs = autoSoftCommitMaxDocs;
  }

  public int getAutoSoftCommitMaxTime() {
    return autoSoftCommitMaxTime;
  }

  public void setAutoSoftCommitMaxTime(int autoSoftCommitMaxTime) {
    this.autoSoftCommitMaxTime = autoSoftCommitMaxTime;
  }

  public boolean isOpenSearcher() {
    return openSearcher;
  }

  public void setOpenSearcher(boolean openSearcher) {
    this.openSearcher = openSearcher;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public int getCommitIntervalLowerBound() {
    return commitIntervalLowerBound;
  }

  public void setCommitIntervalLowerBound(int commitIntervalLowerBound) {
    this.commitIntervalLowerBound = commitIntervalLowerBound;
  }

  public boolean isCommitWithinSoftCommit() {
    return commitWithinSoftCommit;
  }

  public void setCommitWithinSoftCommit(boolean commitWithinSoftCommit) {
    this.commitWithinSoftCommit = commitWithinSoftCommit;
  }

  public boolean isIndexWriterCloseWaitsForMerges() {
    return indexWriterCloseWaitsForMerges;
  }

  public void setIndexWriterCloseWaitsForMerges(boolean indexWriterCloseWaitsForMerges) {
    this.indexWriterCloseWaitsForMerges = indexWriterCloseWaitsForMerges;
  }
}
