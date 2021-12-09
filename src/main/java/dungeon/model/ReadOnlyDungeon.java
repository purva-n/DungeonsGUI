package dungeon.model;

import java.util.List;
import java.util.Map;

public interface ReadOnlyDungeon {
  int getDimensionRow();

  int getDimensionColumn();

  int getInterconnectivity();

  boolean isWrapping();

  int getTreasureArrowPercent();

  int getNumberOfOtyughs();

  boolean gameOver();

  boolean gameBegin();

  List<Direction> getPlayerMoves();

  SmellFactor getPlayerSenseFactor();

  String getPlayerInfo();

  Map<TreasureType, Integer> getPlayersCollectedTreasure();

  /**
   * Method to get the start {@link Location} of the dungeon from where the Player needs to begin.
   *
   * @return Location of the start of the Quest.
   */
  Location getStartLoc();

  /**
   * Method to get the end {@link Location} of the dungeon to where the Player needs to stop.
   *
   * @return Location of the end of the Quest.
   */
  Location getEndLoc();

  /**
   * Method to get the player's current Location.
   *
   * @return {@link Location} of the player.
   */
  Location getPlayerLocation();

  /**
   * Method to get the count of the locations in the dungeon where the arrows are placed.
   *
   * @return integer value of the location count where arrows are placed.
   */
  int getDungeonArrowContainingLocationCount();

  /**
   * Method to get the count of the caves in the dungeon where the treasures are placed.
   *
   * @return integer value of the cave count where the treasures are placed.
   */
  int getTreasureContainingCavesCount();

  /**
   * Method to get the count of the arrows possessed by the player.
   *
   * @return integer value of the player's arrows possessed.
   */
  int getPlayerArrowCount();



  /**
   * Method to get whether the player is alive or not.
   *
   * @return boolean value whether the player is alive or not.
   */
  boolean playerIsAlive();

  Player getPlayer();

  Location getLocationAt(int i, int j);

  boolean hasPlayerAt(int row, int col);

  Object getValidDirectionOfLocationAt(int row, int column);
}
