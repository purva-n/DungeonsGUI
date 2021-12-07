package dungeon.model;

/**
 * Enum to specify the type and the points associated to each Treasure Type.
 */
public enum TreasureType {
  RUBY(5),
  SAPPHIRE(10),
  DIAMOND(20);

  private final int points;
  TreasureType(int points) {
    this.points = points;
  }

  public int getPoints() {
    return points;
  }
}
