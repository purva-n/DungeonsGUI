package dungeon.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.javatuples.Pair;

import random.Randomizer;

/**
 * Implementation of the {@link Player} interface defines the behaviour of the player in the game.
 */
public class PlayerImpl implements Player {
  private int currentRow;
  private int currentCol;
  private Map<TreasureType, Integer> treasureCollected;
  private Location atLocation;
  private int arrowsQuantity;
  private boolean isAlive;
  private Pair<SmellFactor, WindFactor> senseFactor;

  /**
   * Constructor to construct the initial state of the PLayer before beginning the Game.
   */
  public PlayerImpl() {
    // empty constructor to suppress errors from copy constructor
    this.treasureCollected = new HashMap<>();
    this.treasureCollected.put(TreasureType.DIAMOND, 0);
    this.treasureCollected.put(TreasureType.SAPPHIRE, 0);
    this.treasureCollected.put(TreasureType.RUBY, 0);
    this.isAlive = true;
    this.arrowsQuantity = 3;
  }

  /**
   * Copy constructor to the Player class.
   *
   * @param player Player whose copy needs to be created.
   */
  public PlayerImpl(Randomizer rnd, Player player) {
    this.currentRow = player.getCurrentRow();
    this.currentCol = player.getCurrentCol();
    this.atLocation = player.getAtLocation();
    this.treasureCollected = player.getTreasureCollected();
    this.isAlive = player.isAlive();
    this.arrowsQuantity = player.getArrowQuantity();
    this.senseFactor = player.getSenseFactor(rnd);
  }

  @Override
  public int getCurrentRow() {
    return currentRow;
  }

  @Override
  public int getCurrentCol() {
    return currentCol;
  }

  @Override
  public Location getAtLocation() {
    return atLocation;
  }

  @Override
  public Pair<SmellFactor, WindFactor> moveToLocation(Randomizer rnd, Direction whichDirection,
                                                      boolean isWrap) {

    if ((!atLocation.getHasConnectionAt().contains(whichDirection))) {
      throw new IllegalArgumentException("No door here");
    }

    if (rnd == null) {
      throw new IllegalArgumentException("Random number generator cannot be null.");
    }

    atLocation.setHasPlayer(false);
    Location newLoc;
    if (isWrap) {
      int newRow = currentRow + whichDirection.getRow() % DungeonGame.dungeonRow;
      int newCol = currentCol + whichDirection.getColumn() % DungeonGame.dungeonCol;
      if (newRow < 0 || newRow >= DungeonGame.dungeonRow) {
        newRow = (DungeonGame.dungeonRow + newRow) % DungeonGame.dungeonRow;
      }

      if (newCol < 0 || newCol >= DungeonGame.dungeonCol) {
        newCol = (DungeonGame.dungeonCol + newCol) % DungeonGame.dungeonCol;
      }
      newLoc = DungeonGame.getDungeon()
              .get(newRow)
              .get(newCol);
    } else {
      newLoc = DungeonGame.getDungeon().get(currentRow + whichDirection.getRow())
              .get(currentCol + whichDirection.getColumn());
    }
    this.atLocation = null;
    Pair<SmellFactor, WindFactor> senseFactor = this.setLocation(newLoc, rnd);
    this.senseFactor = senseFactor;

    return senseFactor;
  }

  @Override
  public List<Direction> getMovesPossible() {
    return new ArrayList<>(atLocation.getHasConnectionAt());
  }

  @Override
  public Map<TreasureType, Integer> getTreasureCollected() {
    return new HashMap<>(treasureCollected);
  }

  @Override
  public Pair<SmellFactor, WindFactor> setLocation(Location l, Randomizer rnd) {
    this.atLocation = l;
    this.currentRow = l.getR();
    this.currentCol = l.getC();

    l.hasTraversed();
    l.setHasPlayer(true);

    return checkSenseFactor(rnd);
  }

  @Override
  public void collectTreasure(TreasureType pickUp) {
    Treasure t = atLocation.getTreasure();
    if (t.getStones() == null) {
      throw new NoSuchElementException("No treasure here.");
    }

    if (!t.getStones().containsKey(pickUp)) {
      throw new IllegalStateException("Didn't find this treasure here.");
    }

    this.treasureCollected.put(pickUp, this.treasureCollected.get(pickUp)
            + t.getStones().get(pickUp));

    t.updateTreasureQuantity(pickUp, t.getStones().get(pickUp) * -1);
  }

  @Override
  public int shoot(String direction, String distanceToShoot) {

    if (arrowsQuantity <= 0) {
      return -1;
    }

    if (!checkValidDirection(direction)) {
      throw new IllegalArgumentException("Incorrect direction");
    }

    Direction dirToGo;
    int otyghStatus = 0;
    int distance;
    Location current = getAtLocation();
    try {
      distance = Integer.parseInt(distanceToShoot);
    } catch (NoSuchElementException ex) {
      throw new NoSuchElementException();
    }

    switch (direction) {
      case "N":
        dirToGo = Direction.NORTH;
        break;
      case "E":
        dirToGo = Direction.EAST;
        break;
      case "W":
        dirToGo = Direction.WEST;
        break;
      case "S":
        dirToGo = Direction.SOUTH;
        break;
      default:
        throw new IllegalArgumentException("Incorrect direction.");
    }

    this.arrowsQuantity -= 1;
    while (distance != 0) {
      current = current.getLocationAt(dirToGo);

      if (current.getType() == LocationType.TUNNEL) {
        Direction opposite = dirToGo.getOpposite();

        dirToGo = current.getHasConnectionAt().stream().filter(d -> !d.equals(opposite))
                .findFirst().orElse(Direction.ZERO);
      } else if (current.getType() == LocationType.CAVE) {
        distance--;
      }
    }

    if (current.getOtyugh().getQuantity() > 0 && current.getOtyugh().getHealth() > 50) {
      current.getOtyugh().updateHealth(-50);
      otyghStatus = 1;
    } else if (current.getOtyugh().getQuantity() > 0 && current.getOtyugh().getHealth() == 50) {
      current.getOtyugh().updateHealth(-50);

      current.getOtyugh().updateQuantity(current.getOtyugh().getQuantity() * -1);
      otyghStatus = 2;
    }

    return otyghStatus;
  }

  @Override
  public void collectArrow() {
    Arrow a = atLocation.getArrow();
    if (a.getQuantity() > 0) {
      this.arrowsQuantity = this.arrowsQuantity + a.getQuantity();

      a.updateQuantity(a.getQuantity() * -1); // empty the arrows in location
    }
  }

  @Override
  public String toString() {
    return getAtLocation().toString() + "\n"
            + "Player has arrows: "
            + getArrowQuantity();
  }

  private Pair<SmellFactor, WindFactor> checkSenseFactor(Randomizer rnd) {
    int otyughCount = 0;
    int pitCount = 0;

    if (atLocation.getPit().getQuantity() > 0) {
      isAlive = false;
      //atLocation.setPlayer(null, rnd);
      return new Pair<>(SmellFactor.NO_SMELL, WindFactor.IN_PIT);
    }
    if (atLocation.getOtyugh().getQuantity() > 0) {
      if (atLocation.getOtyugh().getHealth() == 50) {
        int chance = rnd.getRandomFromBound(2);
        if (chance == 0) {
          return new Pair<>(SmellFactor.WITH_OTYUGH_SAVED, WindFactor.NO_WIND);
        }
      } else {
        isAlive = false;
        //atLocation.setPlayer(null, rnd);
        return new Pair<>(SmellFactor.WITH_OTYUGH_DEAD, WindFactor.NO_WIND);
      }
    }

    for (Location l : atLocation.getConnectedDirLoc().values()) {
      if (l.getOtyugh().getQuantity() > 0) {
        otyughCount++;
      }
      if (l.getPit().getQuantity() > 0) {
        pitCount++;
      }

      if (otyughCount > 0 && pitCount > 0) {
        return new Pair<>(SmellFactor.MORE_PUNGENT, WindFactor.MORE_POWERFUL_WIND);
      }
    }

    for (Location l : atLocation.getConnectedDirLoc().values()) {
      for (Location tempLoc : l.getConnectedDirLoc().values()) {
        if (tempLoc.getOtyugh().getQuantity() > 0) {
          otyughCount++;
        }

        if (tempLoc.getPit().getQuantity() > 0) {
          pitCount++;
        }
      }
    }

    if (otyughCount >= 2 && pitCount >= 2) {
      return new Pair<>(SmellFactor.MORE_PUNGENT, WindFactor.MORE_POWERFUL_WIND);
    } else if (otyughCount == 1 && pitCount == 1) {
      return new Pair<>(SmellFactor.LESS_PUNGENT, WindFactor.LESS_POWERFUL_WIND);
    } else if (otyughCount >= 2 && pitCount == 1) {
      return new Pair<>(SmellFactor.MORE_PUNGENT, WindFactor.LESS_POWERFUL_WIND);
    } else if (otyughCount == 1 && pitCount >= 2) {
      return new Pair<>(SmellFactor.LESS_PUNGENT, WindFactor.MORE_POWERFUL_WIND);
    }

    return new Pair<>(SmellFactor.NO_SMELL, WindFactor.NO_WIND);
  }

  @Override
  public boolean isAlive() {
    return isAlive;
  }

  @Override
  public int getArrowQuantity() {
    return arrowsQuantity;
  }

  @Override
  public boolean checkValidDirection(String direction) {
    for (Direction d : this.getMovesPossible()) {
      if (direction.equals(d.name().substring(0, 1))) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Pair<SmellFactor, WindFactor> getSenseFactor(Randomizer rnd) {
    return checkSenseFactor(rnd);
  }
}
