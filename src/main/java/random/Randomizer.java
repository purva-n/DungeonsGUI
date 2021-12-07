package random;

/**
 * Interface to generate random numbers.
 */
public interface Randomizer {
  /**
   * Method to generate random number from specifies range.
   *
   * @param start range start integer.
   * @param end   range end integer.
   * @return integer between start and end.
   */
  int getRandomFromRange(int start, int end);

  /**
   * Method to generate random number from 0 to integer bound.
   *
   * @param bound max limit.
   * @return integer between 0 and bound.
   */
  int getRandomFromBound(int bound);
}
