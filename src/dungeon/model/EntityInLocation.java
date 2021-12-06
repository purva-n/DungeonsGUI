package dungeon.model;

/**
 * Interface which provides functionalities for the Entities that can be in the {@link Location}s.
 * e.g. {@link Otyugh}s, {@link Arrow}s, {@link Treasure}s.
 */
interface EntityInLocation {
  void updateQuantity(int quantity);

  void updateTreasureQuantity(TreasureType stone, int quantity);
}
