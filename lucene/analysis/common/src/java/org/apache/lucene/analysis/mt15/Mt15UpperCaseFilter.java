package org.apache.lucene.analysis.mt15;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharacterUtils;

/**
 *
 * Project master thesis 2015 (Mt15).
 *
 * Created by onur kasimlar on 20.10.14.
 */
public class Mt15UpperCaseFilter extends TokenFilter {

  private final CharacterUtils charUtils = CharacterUtils.getInstance();
  private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

  /**
   * Create a new Mt15UpperCaseFilter, that normalizes token text to upper case.
   *
   * @param in TokenStream to filter
   */
  public Mt15UpperCaseFilter(TokenStream in) {
    super(in);
  }

  @Override
  public final boolean incrementToken() throws IOException {
    if (input.incrementToken()) {
      charUtils.toUpperCase(termAtt.buffer(), 0, termAtt.length());
      return true;
    } else
      return false;
  }

}
