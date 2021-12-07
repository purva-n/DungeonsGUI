package random;

/**
 * Class that creates random numbers in fixed fashion.
 */
public class RandomFixed implements Randomizer {

  private int count = 0;

  @Override
  public int getRandomFromRange(int start, int end) {
    return ((start + end) / 2);
  }

  @Override
  public int getRandomFromBound(int bound) {
    if (count + 2 >= bound) {
      count = (count + 2) % bound;
    } else {
      count = count + 2;
    }

    return count;
  }
}
