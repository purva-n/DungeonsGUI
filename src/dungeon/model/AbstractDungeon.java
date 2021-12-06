package dungeon.model;

import java.util.List;
import java.util.Map;
import random.Randomizer;

/**
 * Abstract class that implements {@link Dungeon}.
 * Implements functionalities for the Dungeon interface.
 */
public abstract class AbstractDungeon implements Dungeon {
  @Override
  public abstract boolean isWrapping();

  @Override
  public abstract int getDimensionRow();

  @Override
  public abstract int getDimensionColumn();

  @Override
  public abstract int getInterconnectivity();

  @Override
  public abstract int startQuest();

  @Override
  public List<Direction> getPlayerMoves() {
    return null;
  }

  @Override
  public int getPlayerSmellFactor() {
    return 0;
  }

  @Override
  public abstract String getPlayerInfo();

  @Override
  public abstract int makePlayerShoot(String direction, String distanceToShoot);

  @Override
  public abstract void makePlayerCollectArrow();

  @Override
  public abstract void makePlayerCollectTreasure(String stone);

  @Override
  public Map<TreasureType, Integer> getPlayersCollectedTreasure() {
    return null;
  }

  @Override
  public Player getPlayer() {
    return null;
  }

  @Override
  public abstract Location getStartLoc();

  @Override
  public abstract Location getEndLoc();

  @Override
  public int movePlayer(String direction, Randomizer rnd) {
    return 0;
  }

  @Override
  public boolean gameOver() {
    return false;
  }

  @Override
  public int getDungeonArrowContainingLocationCount() {
    return 0;
  }

  @Override
  public int getTreasureContainingCavesCount() {
    return 0;
  }

  @Override
  public int getPlayerArrowCount() {
    return 0;
  }

  @Override
  public Location getPlayerLocation() {
    return null;
  }

  @Override
  public boolean playerIsAlive() {
    return false;
  }

  public abstract Location getLocationAt(int row, int column);

  @Override
  public void setRow(Integer row) {
  }

  @Override
  public void setColumn(Integer col) {

  }

  @Override
  public void setInterconnectivity(Integer interconnectivity) {

  }

  @Override
  public void setTreasurePercent(Integer treasurePercent) {

  }

  @Override
  public void setNumOtyughs(Integer numOtyughs) {
  }

  @Override
  public void setIsWrap(boolean isWrap) {

  }

  @Override
  public int getTreasureArrowPercent() {
    return 0;
  }

  @Override
  public int getNumberOfOtyughs() {
    return 0;
  }
}
