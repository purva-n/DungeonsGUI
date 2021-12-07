package dungeon.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import random.Randomizer;

/**
 * Location class specifies the functions and behaviour of Locations inside the {@link Dungeon}.
 * A {@link Dungeon} constains a grid of Locations.
 * Each Location has a {@link LocationType}, where it could be a CAVE or a TUNNEL.
 */
public class Location {
  private final int rowNum;
  private final int columnNum;
  private int id;
  //private final Set<Direction> hasConnectionAt;
  //private final List<Location> isConnectedToLoc;
  private Map<Direction, Location> connectedDirLoc;
  private LocationType type;
  private final List<EntityInLocation> entitiesInLoc;
  private static int count;
  Player player;
  private boolean isTraversed;
  private boolean hasPlayer = false;
  private int level;

  /**
   * Constructor to construct each Location inside the {@link Dungeon}.
   *
   * @param row Location at which row in the Dungeon.
   * @param col Location at which column in the DUngeon.
   */
  public Location(int row, int col) {
    this.rowNum = row;
    this.columnNum = col;
    this.entitiesInLoc = new ArrayList<>(3);   //restrict adding more elements to list
    this.id = count++ % (DungeonGame.dungeonRow * DungeonGame.dungeonCol);
    //this.hasConnectionAt = new HashSet<>();
    this.type = LocationType.CAVE;

    //this.isConnectedToLoc = new ArrayList<>();
    this.connectedDirLoc = new HashMap<>();
    this.isTraversed = false;

    this.entitiesInLoc.add(new Treasure());
    this.entitiesInLoc.add(new Otyugh());
    this.entitiesInLoc.add(new Arrow());
    this.entitiesInLoc.add(new Pit());

  }

  Location(Location loc) {
    this.rowNum = loc.getR();
    this.columnNum = loc.getC();
    this.entitiesInLoc = loc.getEntitiesInLoc();
    //this.hasConnectionAt = new HashSet<>();
    //this.hasConnectionAt.addAll(loc.getHasConnectionAt());

    //this.isConnectedToLoc = loc.getIsConnectedToLoc();
    this.isTraversed = loc.getIsTraversed();
    this.type = loc.getType();
  }

  List<EntityInLocation> getEntitiesInLoc() {
    return new ArrayList<>(entitiesInLoc);
  }

  /**
   * Method to get the level of the Location for calculation of Start - End Distance.
   *
   * @return integer value of level.
   */
  int getLevel() {
    return level;
  }

  /**
   * Method to set the level of the Location for calculation of Start - End Distance.
   *
   * @param level integer value of the level.
   */
  void setLevel(int level) {
    this.level = level;
  }

  /**
   * Method to get the type of the Location.
   *
   * @return {@link LocationType}.
   */
  LocationType getType() {
    return type;
  }

  /**
   * Method to set the treasures of type {@link TreasureType} inside {@link Dungeon}.
   *
   * @param caveTreasure Map of {@link TreasureType}s and their quantities.
   */
  void setTreasure(TreasureType caveTreasure, int quantity) {
    (this.entitiesInLoc.get(Constants.TREASURE.ordinal())).updateQuantity(quantity);
    ((Treasure) this.entitiesInLoc.get(Constants.TREASURE.ordinal())).addStone(caveTreasure,
            quantity);
  }

  void setArrows(int quantity) {
    this.entitiesInLoc.get(Constants.ARROW.ordinal()).updateQuantity(quantity);
  }

  void addOtyugh() {
    this.entitiesInLoc.get(Constants.OTYUGH.ordinal()).updateQuantity(1);
    this.getOtyugh().setHealth(this.getOtyugh().getHealth() * this.getOtyugh().getQuantity());
  }

  void addPit() {
    this.entitiesInLoc.get(Constants.PIT.ordinal()).updateQuantity(1);
  }

  /**
   * Method to set the {@link Player} at the referenced Location.
   *
   * @param player {@link Player} to be positioned at this Location.
   */
  void setPlayer(Player player, Randomizer rnd) {
    this.player = player;
    player.setLocation(this, rnd);
  }

  public Treasure getTreasure() {
    return (Treasure) entitiesInLoc.get(Constants.TREASURE.ordinal());
  }

  public Arrow getArrow() {
    return ((Arrow) entitiesInLoc.get(Constants.ARROW.ordinal()));
  }

  public Otyugh getOtyugh() {
    return ((Otyugh) entitiesInLoc.get(Constants.OTYUGH.ordinal()));
  }

  public AbstractEntity getPit() {
    return ((Pit) entitiesInLoc.get(Constants.PIT.ordinal()));
  }

  public List<Direction> getHasConnectionAt() {
    List<Direction> connections = new ArrayList<>();
    connections.addAll(connectedDirLoc.keySet());

    return connections;
  }

  /**
   * Method to get the row number of the Location.
   *
   * @return integer value of the row number.
   */
  int getR() {
    return rowNum;
  }

  /**
   * Method to get the column number of the Location.
   *
   * @return integer value of the column number.
   */
  int getC() {
    return columnNum;
  }

  private void updateConnectionNorthSouth(Location other) {
    if (this.rowNum < other.getR()) {
      //hasConnectionAt.add(Direction.SOUTH);
      connectedDirLoc.put(Direction.SOUTH, other);
    } else {
      //hasConnectionAt.add(Direction.NORTH);
      connectedDirLoc.put(Direction.NORTH, other);
    }
  }

  private void updateConnectionEastWest(Location other) {
    if (this.columnNum < other.getC()) {
      //hasConnectionAt.add(Direction.EAST);
      connectedDirLoc.put(Direction.EAST, other);
    } else {
      //hasConnectionAt.add(Direction.WEST);
      connectedDirLoc.put(Direction.WEST, other);
    }
  }

  void updateConnection(Location other, boolean isWrap, int drow, int dcol) {

    if (this.rowNum != other.getR()) {
      if (isWrap) {
        if (other.getR() - this.rowNum == drow - 1) {
          //hasConnectionAt.add(Direction.NORTH);
           connectedDirLoc.put(Direction.NORTH, other);
        } else if (this.rowNum - other.getR() == drow - 1) {
          //hasConnectionAt.add(Direction.SOUTH);
          connectedDirLoc.put(Direction.SOUTH, other);
        } else {
          updateConnectionNorthSouth(other);
        }
      } else {
        updateConnectionNorthSouth(other);
      }
    }

    if (this.columnNum != other.getC()) {
      if (isWrap) {
        if (other.getC() - this.columnNum == dcol - 1) {
          //hasConnectionAt.add(Direction.WEST);
          connectedDirLoc.put(Direction.WEST, other);
        } else if (this.columnNum - other.getC() == dcol - 1) {
          //hasConnectionAt.add(Direction.EAST);
          connectedDirLoc.put(Direction.EAST, other);
        } else {
          updateConnectionEastWest(other);
        }
      } else {
        updateConnectionEastWest(other);
      }
    }

    //updateConnectedToLoc(other);
  }

  void updateConnectionId(int id) {
    this.id = id;
    for (Location l : connectedDirLoc.values()) {
      if (l.getConnectionId() != id) {
        l.updateConnectionId(id);
      }
    }
  }

  int getConnectionId() {
    return id;
  }

//  private void updateConnectedToLoc(Location l) {
//    isConnectedToLoc.add(l);
//  }

//  public List<Location> getIsConnectedToLoc() {
//    return isConnectedToLoc;
//  }

  void updateLocationType() {
    type = LocationType.TUNNEL;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o instanceof Location) {
      Location l = (Location) o;

      return this.rowNum == l.getR() && this.columnNum == l.getC();
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.rowNum, this.columnNum);
  }

  void hasTraversed() {
    isTraversed = true;
  }

  void hasNotTraversed() {
    isTraversed = false;
  }

  public boolean getIsTraversed() {
    return isTraversed;
  }

  void setHasPlayer(boolean has) {
    hasPlayer = has;
  }

  boolean getHasPlayer() {
    return hasPlayer;
  }

  public Map<Direction, Location> getConnectedDirLoc() {
    return new HashMap<>(connectedDirLoc);
  }

  @Override
  public String toString() {
    StringBuilder locInfo = new StringBuilder();
    locInfo.append("You are in a ")
            .append(type)
            .append(".\n")
            .append("You find here: \n");

    if (getTreasure().getQuantity() > 0) {
      for (Map.Entry<TreasureType, Integer> t : getTreasure().getStones().entrySet()) {
        locInfo.append(t.getValue()).append(" ")
                .append(t.getKey().name().toLowerCase(Locale.ROOT))
                .append("(s)\n");
      }
    }

    if (getArrow().getQuantity() > 0) {
      locInfo.append(getArrow().getQuantity()).append(" ")
              .append("arrow(s)")
              .append("\n");
    }

    return locInfo.toString();
  }

  public String getRowCol() {
    return "R:" + rowNum + " C:" + columnNum;
  }

  Location getLocationAt(Direction d) {
    return connectedDirLoc.get(d);
  }
}
