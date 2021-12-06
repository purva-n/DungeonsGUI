package dungeon.model;

/**
 * Class that represents Otyugh / Monster that lives in Caves of the Dungeon.
 * Otyugh may eat {@link Player} when injured.
 * Otygh will kill {@link Player} if it is not injured.
 */
public class Otyugh extends AbstractEntity {
  private int health;

  public Otyugh() {
    health = 100;
  }

  int getHealth() {
    return health;
  }

  void updateHealth(int deductHealth) {
    health += deductHealth;
  }

  void setHealth(int health) {
    this.health = health;
  }

  @Override
  public void updateTreasureQuantity(TreasureType stone, int quantity) {
    // no implementation because it is not related to treasure.
  }
}
