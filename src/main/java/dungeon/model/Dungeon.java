package dungeon.model;

import org.javatuples.Pair;

/**
 * Interface that declares functions and operations performed on the {@link Dungeon}.
 * When building and using it for the Game.
 */
public interface Dungeon extends ReadOnlyDungeon {

  Object getValidDirectionOfLocationAt(int row, int column);

  /**
   * Method to set the player at the start location of the dungeon.
   *
   * @return smell factor at the beginnig of the game before even moving.
   */
  Pair<SmellFactor, WindFactor> startQuest();

  int makePlayerShoot(String direction, String distanceToShoot);

  void makePlayerCollectArrow();

  void makePlayerCollectTreasure(String stone);

  /**
   * Method to move the player to given direction in the dungeon.
   *
   * @param direction {@link Direction} to move the player to.
   * @return smell factor from the {@link Otyugh} of the newly moved location.
   */
  Pair<SmellFactor, WindFactor> movePlayer(String direction);

  void setRow(Integer row);

  void setColumn(Integer col);

  void setInterconnectivity(Integer interconnectivity);

  void setIsWrap(boolean isWrap);

  void setTreasurePercent(Integer treasurePercent);

  void setNumOtyughs(Integer numOtyughs);
}
