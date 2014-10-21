package org.apache.lucene.analysis.mt15;

import org.apache.lucene.analysis.core.LetterTokenizer;
import org.apache.lucene.util.AttributeFactory;

/**
 * Project master thesis 2015 (Mt15)
 *
 * Created by onur kasimlar on 19.10.14.
 */
public class Mt15UpperCaseTokenizer extends LetterTokenizer {

  /**
   * Construct a new Mt15UpperCaseTokenizer.
   */
  public Mt15UpperCaseTokenizer() {
  }

  /**
   * Creates a new {@link Mt15UpperCaseTokenizer} instance
   *
   * @param factory the attribute factory to use for this {@link org.apache.lucene.analysis.Tokenizer}
   */
  public Mt15UpperCaseTokenizer(AttributeFactory factory) {
    super(factory);
  }

  /** Converts char to lower case
   * {@link Character#toUpperCase(int)}.
   */
  @Override
  protected int normalize(int c) {
    return Character.toUpperCase(c);
  }
}
