package dungeon.model;

import java.util.HashMap;
import java.util.Map;

public class Treasure extends AbstractEntity {
  Map<TreasureType, Integer> treasure;

  Treasure() {
    treasure = new HashMap<>();
  }

  public Map<TreasureType, Integer> getStones() {
    return new HashMap<>(treasure);
  }

  void addStone(TreasureType stone, int quantity) {
    this.treasure.put(stone, this.treasure.get(stone) == null ? quantity :
            this.treasure.get(stone) + quantity);
  }

  @Override
  public void updateTreasureQuantity(TreasureType stone, int quantity) {
    treasure.put(stone, treasure.get(stone) + quantity);
  }
}
