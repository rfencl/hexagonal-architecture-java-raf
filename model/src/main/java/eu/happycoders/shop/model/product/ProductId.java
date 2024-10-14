package eu.happycoders.shop.model.product;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * A product ID value object (enabling type-safety and validation).
 *
 * @author Sven Woltmann
 */
public record ProductId(String value) {

  private static final int PRODUCT_ID_LENGTH = 8;

  public ProductId {
    Objects.requireNonNull(value, "'value' must not be null");
    if (value.isEmpty()) {
      throw new IllegalArgumentException("'value' must not be empty");
    }
  }

  public static ProductId generateProductId() {
    char[] chars = new char[PRODUCT_ID_LENGTH];

    ThreadLocalRandom random = ThreadLocalRandom.current();
    IntStream.range(0, PRODUCT_ID_LENGTH).forEach(i -> chars[i] = getaChar(random));
    return new ProductId(String.valueOf(chars));
  }

  private static final String ALPHABET = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

  /**
   * Returns the next Random character from the Alphabet.
   *
   * @param random
   * @return
   */
  private static char getaChar(ThreadLocalRandom random) {
    return ALPHABET.charAt(random.nextInt(ALPHABET.length()));
  }
}
