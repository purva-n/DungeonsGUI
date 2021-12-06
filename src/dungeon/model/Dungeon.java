package dungeon.model;

import java.util.List;
import java.util.Map;
import random.Randomizer;

/**
 * Interface that declares functions and operations performed on the {@link Dungeon}.
 * When building and using it for the Game.
 */
public interface Dungeon extends ReadOnlyDungeon {

  /**
   * Method to set the player at the start location of the dungeon.
   *
   * @return smell factor at the beginnig of the game before even moving.
   */
  int startQuest();

  int makePlayerShoot(String direction, String distanceToShoot);

  void makePlayerCollectArrow();

  void makePlayerCollectTreasure(String stone);

//  /**
//   * Method to traverse the {@link Player} from the start location to end location of the dungeon.
//   *
//   * @param acc StringBuilder accumulator to send to the Driver.
//   * @param at  {@link Location} at which the {@link Player} is currently at.
//   * @param end {@link Location} at which the {@link Player} needs to end its Quest.
//   * @return String representation of the total journey of the Player in the Dungeon from start
//   *         location to end location at every move.
//   */
//  String traverseStartToEnd(StringBuilder acc, Location at, Location end, Randomizer rnd);


//  /**
//   * Method to traverse the {@link Player} to all the {@link Location}s inside the Dungeon.
//   *
//   * @param acc StringBuilder accumulator to send to the Driver.
//   * @param at  {@link Location} at which the {@link Player} is currently at.
//   * @return String representation of the total journey of the Player in the Dungeon to all
//   *         the {@link Location}s in the dungeon at every move.
//   */
//  String traverseDungeon(StringBuilder acc, Location at, Randomizer rnd);

//  /**
//   * Method to get the shortest distance between the start and the end {@link Location} in Dungeon.
//   *
//   * @return integer value of the shortest distance between the start and end Locations in Dungeon.
//   */
//  int getStartEndLength();

  /**
   * Method to move the player to given direction in the dungeon.
   *
   * @param direction {@link Direction} to move the player to.
   * @param rnd       Random number generation.
   * @return smell factor from the {@link Otyugh} of the newly moved location.
   */
  int movePlayer(String direction, Randomizer rnd);

  void setRow(Integer row);

  void setColumn(Integer col);

  void setInterconnectivity(Integer interconnectivity);

  void setIsWrap(boolean isWrap);

  void setTreasurePercent(Integer treasurePercent);

  void setNumOtyughs(Integer numOtyughs);
}
