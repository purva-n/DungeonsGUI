package dungeon.model;

public enum WindFactor {
  NO_WIND(0),
  LESS_POWERFUL_WIND(1),
  MORE_POWERFUL_WIND(2),
  IN_PIT(4);

  private final int windFactor;
  WindFactor(int i) {
    this.windFactor = i;
  }

  public int getWindFactor() {
    return windFactor;
  }
}
