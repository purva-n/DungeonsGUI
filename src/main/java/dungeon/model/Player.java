package dungeon.model;

import org.javatuples.Pair;

import java.util.List;
import java.util.Map;
import random.Randomizer;

/**
 * Interface that defines PLayer of game and its behavior.
 */
public interface Player {

  /**
   * Method to get the row of Location of the Player.
   *
   * @return integer value of the row.
   */
  int getCurrentRow();

  /**
   * Method to get the column of Location of the Player.
   *
   * @return integer value of the column.
   */
  int getCurrentCol();

  /**
   * Method to move Player from current Location to one of the possible {@link Direction}s.
   *
   * @param whichDirection {@link Direction}.
   */
  SmellFactor moveToLocation(Randomizer rnd, Direction whichDirection, boolean isWrap);

  /**
   * Method to get the possible {@link Direction}s where the Player can move to next.
   *
   * @return List of possible moves / {@link Direction}s.
   */
  List<Direction> getMovesPossible();

  /**
   * Method to get the total reasure collected.
   *
   * @return map of treasure collected and their quantities.
   */
  Map<TreasureType, Integer> getTreasureCollected();

  /**
   * Get the current location of the player.
   *
   * @return Location of the player at.
   */
  Location getAtLocation();

  /**
   * Method to set player location at specified location.
   *
   * @param l where the player needs to be placed.
   */
  SmellFactor setLocation(Location l, Randomizer rnd);

  /**
   * Method to collect treasure from the Location/ Cave currently at.
   */
  void collectTreasure(TreasureType stone);

  /**
   * Method to pick up arrow from current {@link Location}.
   */
  void collectArrow();

  /**
   * Method to shoot an {@link Arrow} to intend to kill the {@link Otyugh}.
   *
   * @param direction       {@link Direction} to which to aim the arrow.
   * @param distanceToShoot String representation of numeric number of caves to target the Otyugh.
   * @return Otyugh status being hit, injured or not hit by the shoot.
   */
  ShootStatus shoot(String direction, String distanceToShoot);

  /**
   * Method to get whether Player is Alive or not.
   *
   * @return boolean value of whether Player is Alive or not.
   */
  boolean isAlive();

  /**
   * Method to get the arrow quantity possessed by the player.
   *
   * @return integer value of the arrow quantity possessed.
   */
  int getArrowQuantity();

  /**
   * Method to check whether given String representation direction is one of the {@link Direction}s.
   *
   * @param direction String represention/ initial of the intended direction.
   * @return whether given Direction initial is one of the {@link Direction}s.
   */
  boolean checkValidDirection(String direction);

  /**
   * Method to get the smell of the Otyugh from the position at which the Player is.
   *
   * @param rnd random number generator.
   * @return integer value of the smell of the Otyugh in increasing sense.
   */
  SmellFactor getSenseFactor(Randomizer rnd);
}
