package org.apache.lucene.analysis.mt15;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.util.Version;

/**
 *
 * Project master thesis 2015 (Mt15)
 *
 * Created by onur kasimlar on 19.10.14.
 *
 * TODO remove this link after studying: http://www.hascode.com/2014/07/lucene-by-example-specifying-analyzers-on-a-per-field-basis-and-writing-a-custom-analyzertokenizer/
 */
public class Mt15UpperCaseAnalyzer extends Analyzer {

  private final Version version;

  public Mt15UpperCaseAnalyzer(final Version version) {
    this.version = version;
  }


  /**
   * Creates a new {@link org.apache.lucene.analysis.Analyzer.TokenStreamComponents} instance for this analyzer.
   *
   * @param fieldName the name of the fields content passed to the
   *                  {@link org.apache.lucene.analysis.Analyzer.TokenStreamComponents} sink as a reader
   * @return the {@link org.apache.lucene.analysis.Analyzer.TokenStreamComponents} for this analyzer.
   */
  @Override
  protected TokenStreamComponents createComponents(String fieldName) {
    Tokenizer tokenizer = new Mt15UpperCaseTokenizer();
    TokenStream filter = new LowerCaseFilter(tokenizer);
    return new TokenStreamComponents(tokenizer, filter);
  }
}
