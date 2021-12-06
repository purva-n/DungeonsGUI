package random;

import java.util.Random;

/**
 * Class that provides true random numbers for actual game play execution.
 */
public class TrueRandom implements Randomizer {

  private Random rnd = new Random();

  @Override
  public int getRandomFromRange(int start, int end) {
    try {
      return start + rnd.nextInt(Math.abs(end - start));
    } catch (IllegalArgumentException iae) {
      return start;
    }
  }

  @Override
  public int getRandomFromBound(int bound) {
    return rnd.nextInt(bound);
  }
}
