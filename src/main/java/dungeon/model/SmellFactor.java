package dungeon.model;

public enum SmellFactor {
  NO_SMELL(0),
  LESS_PUNGENT(1),
  MORE_PUNGENT(2),
  WITH_OTYUGH_SAVED(3),
  WITH_OTYUGH_DEAD(4);

  private final int smell;
  SmellFactor(int i) {
    smell = i;
  }

  public int getSmellFactor() {
    return smell;
  }
}
