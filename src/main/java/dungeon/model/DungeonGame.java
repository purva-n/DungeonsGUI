package dungeon.model;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import random.Randomizer;
import random.TrueRandom;

/**
 * DungeonGame is the Implementation for the interface {@link Dungeon} which represents functions.
 * which are related to the Dungeon such as building the dungeon, distributing treasure inside ,
 * dungeon and several other related functions.
 */
public class DungeonGame implements Dungeon {
  static int dungeonRow;
  static int dungeonCol;
  private int interconnectivity;
  private int treasureArrowPercent;
  private int numOtyughs;

  private static List<List<Location>> dungeon;
  private boolean isWrapping = false;
  private static Player player;
  List<Edge> potentialPaths;
  private List<Edge> remainingEdges;
  private List<Location> caves;
  private Location start;
  private Location end;
  private boolean gameStarted;
  private Randomizer rnd;

  public DungeonGame(Randomizer rnd) {
    gameStarted = false;
    this.rnd = rnd;
  }

  /**
   * Constructor to initialise the Dungeon with its initial configurations.
   *
   * @param rows              Number of rows of Locations to be constructed in the Dungeon.
   * @param columns           Number of columns of Locations to be constructed in the Dungeon.
   * @param interconnectivity Defines the additional paths inside the Dungeon.
   * @param numOtyugh         Defines the number of
   * @throws IllegalStateException if the start and end locations of the dungeon are at a distance.
   *                               less than 5 units.
   */
  public DungeonGame(Randomizer rnd, int rows, int columns, int interconnectivity, int numOtyugh)
          throws IllegalStateException {
    if (rows < 0 || columns < 0 || interconnectivity < 0 || numOtyugh < 1) {
      throw new IllegalArgumentException("Dimensions must be positive. | There should be atleast " +
              "one Otyugh");
    }

    this.rnd = rnd;
    constructDungeon();
  }

  /**
   * Copy constructor to initialise the initial configurations of the Dungeon from a static Dungeon.
   *
   * @param dungeonStatic {@link DungeonStatic} static dungeon created for testing purposes.
   * @param st            Start Location.
   * @param stop          End Location.
   * @throws IllegalStateException if the start and end locations of the dungeon are at a distance.
   *                               less than 5 units
   */
  public DungeonGame(Randomizer rnd, Dungeon dungeonStatic, Location st, Location stop,
                     int treasureArrowPercent, int numOtyugh) throws IllegalStateException {
    if (dungeonStatic == null || st == null || stop == null) {
      throw new IllegalArgumentException("Null args");
    }

    if (!st.getType().equals(LocationType.CAVE) || !stop.getType().equals(LocationType.CAVE)) {
      throw new IllegalArgumentException("Start and End Locations have to be caves.");
    }

    dungeon = DungeonStatic.getDungeon();
    start = st;
    end = stop;
    player = new PlayerImpl();
    dungeonRow = dungeonStatic.getDimensionRow();
    dungeonCol = dungeonStatic.getDimensionColumn();
    interconnectivity = dungeonStatic.getInterconnectivity();
    isWrapping = dungeonStatic.isWrapping();
    this.treasureArrowPercent = treasureArrowPercent;
    numOtyughs = numOtyugh;
    this.rnd = rnd;


    refactorCavesToTunnels();
    enlistCaves();
    addTreasureToCaves(rnd, treasureArrowPercent);
    addArrowsToLocation(rnd, treasureArrowPercent);
    int startEndLength = calculateStartEndLength();

    if (startEndLength < 5) {
      throw new IllegalStateException("Start and end Locations are too close. Restarting game");
    }

    addOtyughToCaves(rnd, numOtyugh);
    startQuest();
  }

  @Override
  public Player getPlayer() {
    return new PlayerImpl(rnd, player);
  }

  @Override
  public Location getLocationAt(int i, int j) {
    return dungeon.get(i).get(j);
  }

  @Override
  public Location getStartLoc() {
    return start;
  }

  @Override
  public Location getEndLoc() {
    return end;
  }

  static List<List<Location>> getDungeon() {
    return new ArrayList<>(dungeon);  // make deep copy
  }

  @Override
  public int getDimensionRow() {
    return dungeonRow;
  }

  @Override
  public int getDimensionColumn() {
    return dungeonCol;
  }

  @Override
  public int getInterconnectivity() {
    return interconnectivity;
  }

  @Override
  public List<Direction> getPlayerMoves() {
    return new ArrayList<>(player.getMovesPossible());
  }

  @Override
  public Pair<SmellFactor, WindFactor> getPlayerSenseFactor() {
    return player.getSenseFactor(rnd);
  }

  @Override
  public String getPlayerInfo() {
    return player.toString();
  }

  @Override
  public int makePlayerShoot(String direction, String distanceToShoot) {
    return player.shoot(direction, distanceToShoot);
  }

  @Override
  public void makePlayerCollectArrow() {
    player.collectArrow();
  }

  @Override
  public void makePlayerCollectTreasure(String stone) {
    switch (stone) {
      case "ruby":
        player.collectTreasure(TreasureType.RUBY);
        break;
      case "diamond":
        player.collectTreasure(TreasureType.DIAMOND);
        break;
      case "sapphire":
        player.collectTreasure(TreasureType.SAPPHIRE);
        break;
      default:
        throw new IllegalArgumentException("Didn't get you? No treasure like this.");
    }
  }

  @Override
  public Map<TreasureType, Integer> getPlayersCollectedTreasure() {

    return new HashMap<>(player.getTreasureCollected());
  }

  /**
   * Method to get whether constructed dungeon is a wrapping or non=wrapping dungeon.
   *
   * @return whether it is a wrapping dungeon.
   */
  @Override
  public boolean isWrapping() {
    return isWrapping;
  }

  @Override
  public void setRow(Integer row) {
    dungeonRow = row;
  }

  @Override
  public void setColumn(Integer col) {
    dungeonCol = col;
  }

  @Override
  public void setInterconnectivity(Integer interconnectivity) {
    this.interconnectivity = interconnectivity;
  }

  @Override
  public void setIsWrap(boolean isWrap) {
    isWrapping = isWrap;
  }

  @Override
  public void setTreasurePercent(Integer treasurePercent) {
    this.treasureArrowPercent = treasurePercent;
  }

  @Override
  public void setNumOtyughs(Integer numOtyughs) {
    this.numOtyughs = numOtyughs;
  }

  @Override
  public int getTreasureArrowPercent() {
    return treasureArrowPercent;
  }

  @Override
  public int getNumberOfOtyughs() {
    return numOtyughs;
  }

//  @Override
//  public String traverseStartToEnd(StringBuilder acc, Location at, Location end, Randomizer rnd) {
//    if (at == getEndLoc()) {
//      journeyIsEnded = true;
//    } else {
//      for (Location loc : at.getIsConnectedToLoc()) {
//        if (!loc.getIsTraversed()) {
//          player.setLocation(loc, rnd);
//        } else {
//          continue;
//        }
//        acc.append(player).append("\n");
//        // player.collectTreasure();
//        player.collectArrow();
//        acc.append(this).append("\n");
//        acc.append(displayTreasureCollected()).append("\n");
//        acc.append("Moves Possible: ").append(player.getMovesPossible().toString()).append("\n");
//        traverseStartToEnd(acc, player.getAtLocation(), end, rnd);
//        if (journeyIsEnded) {
//          return acc.toString();
//        }
//      }
//    }
//    return acc.toString();
//  }

//  @Override
//  public String traverseDungeon(StringBuilder acc, Location at, Randomizer rnd) {
//    for (Location loc : at.getIsConnectedToLoc()) {
//      if (!loc.getIsTraversed()) {
//        player.setLocation(loc, rnd);
//      } else {
//        continue;
//      }
//      acc.append(this).append("\n");
//      traverseDungeon(acc, player.getAtLocation(), rnd);
//      if (journeyIsEnded) {
//        return acc.toString();
//      }
//    }
//    return acc.toString();
//  }

  @Override
  public Direction getValidDirectionOfLocationAt(int row, int column) {
    Location selectedLocation = dungeon.get(row).get(column);
    Location playerLocation = player.getAtLocation();

    if(playerLocation.getConnectedDirLoc().containsValue(selectedLocation)) {
      for(Map.Entry<Direction, Location> dirloc: playerLocation.getConnectedDirLoc().entrySet()) {
        if(dirloc.getValue().equals(selectedLocation)) {
          return dirloc.getKey();
        }
      }
    }
    return Direction.ZERO;
  }


  @Override
  public Pair<SmellFactor, WindFactor> startQuest() {

    constructDungeon();
    for (int i = 0; i < dungeonRow; i++) {
      for (int j = 0; j < dungeonCol; j++) {
        dungeon.get(i).get(j).hasNotTraversed();
      }
    }

    start.setPlayer(player, rnd);
    player.setLocation(start, rnd);
    gameStarted = true;

    return getPlayerSenseFactor();
  }

  @Override
  public String toString() {
    Queue<String> path = new LinkedList<>();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < dungeonRow; i++) {
      for (int j = 0; j < dungeonCol; j++) {
        Location loc = dungeon.get(i).get(j);

        if (loc.getHasPlayer()) {
          sb.append("P");
        } else if (loc.getOtyugh().getQuantity() > 0) {
          sb.append("O");
        } else if (loc.getType() == LocationType.CAVE) {
          sb.append("C");
        } else {
          sb.append("T");
        }

        if (loc.getHasConnectionAt().contains(Direction.EAST)) {
          sb.append("----");
        } else {
          sb.append("    ");
        }

        if (loc.getHasConnectionAt().contains(Direction.SOUTH)) {
          path.add("|    ");
        } else {
          path.add("     ");
        }
      }
      String souths = String.join("", path);

      sb.append("\n")
              .append(souths)
              .append("\n")
              .append(souths)
              .append("\n");

      path.clear();
    }
    return sb.toString();
  }

  @Override
  public boolean hasPlayerAt(int row, int col) {
    return dungeon.get(row).get(col).getHasPlayer();
  }

  @Override
  public Pair<SmellFactor, WindFactor> movePlayer(String direction) {
      if (!player.checkValidDirection(direction)) {
        throw new IllegalArgumentException("Select from possible moves. try again!");
      }

      switch (direction) {
        case "N":
          return player.moveToLocation(rnd, Direction.NORTH, isWrapping);
        case "S":
          return player.moveToLocation(rnd, Direction.SOUTH, isWrapping);
        case "E":
          return player.moveToLocation(rnd, Direction.EAST, isWrapping);
        case "W":
          return player.moveToLocation(rnd, Direction.WEST, isWrapping);
        default:
          throw new IllegalArgumentException("Invalid direction");
      }
  }

  @Override
  public boolean gameOver() {
    if (player.getAtLocation().equals(end)) {
      return true;
    }

    return !player.isAlive();
  }

  @Override
  public boolean gameBegin() {
    return gameStarted;
  }

  private int calculateStartEndLength() {
    Queue<Location> bfsQueue = new LinkedList<>();
    bfsQueue.add(getStartLoc());
    getStartLoc().hasTraversed();
    getStartLoc().setLevel(0);

    Location removed = getStartLoc();
    while (!bfsQueue.isEmpty()) {
      removed = bfsQueue.remove();
      if (removed == getEndLoc()) {
        return removed.getLevel();
      } else {
        for (Location l : removed.getConnectedDirLoc().values()) {
          if (!l.getIsTraversed()) {
            l.setLevel(removed.getLevel() + 1);
            l.hasTraversed();
            bfsQueue.add(l);
          }
        }
      }
    }
    return removed.getLevel();
  }

  private void refactorCavesToTunnels() {
    for (int i = 0; i < dungeonRow; i++) {
      for (int j = 0; j < dungeonCol; j++) {
        Location loc = dungeon.get(i).get(j);
        if (loc.getHasConnectionAt().size() == 2) {
          // these are tunnels
          loc.updateLocationType();
        }
      }
    }
  }

  private void addInterconnectivityEdges(Randomizer random) {
    if(remainingEdges.size() > 0) {
      for (int i = 0; i < interconnectivity; i++) {
        Edge edge = remainingEdges.get(random.getRandomFromBound(remainingEdges.size()));
        Location l1 = edge.getL1();
        Location l2 = edge.getL2();

        l1.updateConnection(l2, isWrapping, dungeonRow, dungeonCol);
        l2.updateConnection(l1, isWrapping, dungeonRow, dungeonCol);
        remainingEdges.remove(edge);
      }
    }
  }

  private void enlistCaves() {
    caves = new ArrayList<>();
    Location loc;

    for (int i = 0; i < dungeonRow; i++) {
      for (int j = 0; j < dungeonCol; j++) {
        loc = dungeon.get(i).get(j);
        if (loc.getType() == LocationType.CAVE) {
          caves.add(loc);
        }
      }
    }
  }

  private void buildDungeon(Randomizer rnd) throws IllegalStateException {   // kruskal
    // make each cave
    potentialPaths = new ArrayList<>();
    remainingEdges = new ArrayList<>();

    // make list of potential paths
    for (int i = 0; i < dungeonRow; i++) {
      for (int j = 0; j < dungeonCol; j++) {
        if (isWrapping) {
          potentialPaths.add(new Edge(dungeon.get(i).get(j),
                  dungeon.get(i).get((j + 1) % dungeonCol)));
          potentialPaths.add(new Edge(dungeon.get(i).get(j),
                  dungeon.get((i + 1) % dungeonRow).get(j)));
        } else {
          if (i < dungeonRow - 1) {
            potentialPaths.add(new Edge(dungeon.get(i).get(j),
                    dungeon.get(i + 1).get(j)));
          }

          if (j < dungeonCol - 1) {
            potentialPaths.add(new Edge(dungeon.get(i).get(j),
                    dungeon.get(i).get(j + 1)));
          }
        }
      }
    }

    //System.out.println("Potential paths: " + potentialPaths.size());

    // make walls in dungeon
    while (potentialPaths.size() != 0) {
      Edge edge = potentialPaths.get(rnd.getRandomFromBound(potentialPaths.size()));
      Location l1 = edge.getL1();
      Location l2 = edge.getL2();

      if (l1.getConnectionId() != l2.getConnectionId()) {
        l1.updateConnection(l2, isWrapping, dungeonRow, dungeonCol);
        l2.updateConnection(l1, isWrapping, dungeonRow, dungeonCol);

        int l1ConnSize = l1.getConnectedDirLoc().values().size();
        int l2ConnSize = l2.getConnectedDirLoc().values().size();
        if (l1ConnSize == 1) {
          l1.updateConnectionId(l2.getConnectionId());
        } else if (l2ConnSize == 1) {
          l2.updateConnectionId(l1.getConnectionId());
        } else if (l1ConnSize <= l2ConnSize) {
          l1.updateConnectionId(l2.getConnectionId());
        } else {
          l2.updateConnectionId(l1.getConnectionId());
        }
      } else {
        remainingEdges.add(edge);
      }
      potentialPaths.remove(edge);
    }

    //Check if all Locations are connected or are reachable from anywhere in the dungeon
//    if (!checkReachability()) {
//      throw new IllegalStateException("Something went wrong when making the dungeon");
//    }

    // Add remaining edges = interconnectivity
    addInterconnectivityEdges(rnd);

    // Make caves to tunnels
    refactorCavesToTunnels();
  }

  private void addTreasureToCaves(Randomizer rnd, int treasurePercent) {
    try {
      int percentCaves = (caves.size() * treasurePercent / 100);
      int cavesNum = rnd.getRandomFromRange(percentCaves, caves.size());
      for (int i = 0; i < cavesNum; i++) {
        Location cave = caves.get(rnd.getRandomFromBound(caves.size()));

        int quantity = rnd.getRandomFromRange(1, 3);
        TreasureType t = TreasureType.values()[rnd.getRandomFromBound(3)];
        cave.setTreasure(t, quantity);
      }
    } catch (NullPointerException exc) {
      System.err.println("Looks like caves were not enlisted.");
    }
  }

  private void addArrowsToLocation(Randomizer rnd, int arrowPercent) {
    int percentLocs = this.getDimensionRow() * this.getDimensionColumn() * arrowPercent / 100;
    int locNum = rnd.getRandomFromRange(percentLocs, this.getDimensionRow()
            * this.getDimensionColumn());

    List<Location> setLocations = new ArrayList<>();

    for (int i = 0; i < dungeonRow; i++) {
      setLocations.addAll(dungeon.get(i));
    }

    for (int i = 0; i < locNum; i++) {
      Location loc = setLocations.get(rnd.getRandomFromBound(setLocations.size()));

      int quantity = rnd.getRandomFromRange(1, 4);
      loc.setArrows(quantity);
    }
  }

  private void addOtyughToCaves(Randomizer rnd, int numOtyughs) {
    for (int i = 0; i < numOtyughs - 1; i++) {
      Location loc = caves.get(rnd.getRandomFromBound(caves.size()));

      if (loc.equals(this.start)) {
        i -= 1;
        continue;
      }

      loc.addOtyugh();
    }

    this.end.addOtyugh();     //atleast one otyugh at end
  }

  private boolean checkReachability() {
    try {
      int id = dungeon.get(0).get(0).getConnectionId();
      for (int i = 0; i < dungeonRow; i++) {
        for (int j = 0; j < dungeonCol; j++) {
          if (dungeon.get(i).get(j).getConnectionId() != id) {
            return false;
          }
        }
      }

      return true;
    } catch (NullPointerException exc) {
      System.err.println("Dungeon may not have been created.");
    }
    return false;
  }

  @Override
  public int getDungeonArrowContainingLocationCount() {
    int count = 0;
    for (int i = 0; i < dungeonRow; i++) {
      for (int j = 0; j < dungeonCol; j++) {
        if (dungeon.get(i).get(j).getArrow().getQuantity() > 0) {
          count++;
        }
      }
    }

    return count;
  }

  @Override
  public int getTreasureContainingCavesCount() {
    int count = 0;
    for (int i = 0; i < dungeonRow; i++) {
      for (int j = 0; j < dungeonCol; j++) {
        if (dungeon.get(i).get(j).getTreasure().getQuantity() > 0) {
          count++;
        }
        System.out.println(dungeon.get(i).get(j).getTreasure().getQuantity());
      }
    }
    return count;
  }

  @Override
  public int getPlayerArrowCount() {
    return player.getArrowQuantity();
  }

  @Override
  public Location getPlayerLocation() {
    return new Location(player.getAtLocation());
  }

  @Override
  public boolean playerIsAlive() {
    return player.isAlive();
  }

  private void constructDungeon() {

    rnd = new TrueRandom();
    int startEndLength;

    // create a player
    player = new PlayerImpl();

    do {
      // construct dungeon
      if(dungeon != null) {
        dungeon.clear();
        caves.clear();
      }
      dungeon = new ArrayList<>(dungeonRow);
      for (int i = 0; i < dungeonRow; i++) {
        dungeon.add(new ArrayList<>(dungeonCol));
        for (int j = 0; j < dungeonCol; j++) {
          dungeon.get(i).add(new Location(i, j));
        }
      }

      buildDungeon(rnd);
      enlistCaves();
      addTreasureToCaves(rnd, treasureArrowPercent);
      addArrowsToLocation(rnd, treasureArrowPercent);
      addPitToCaves(rnd, numOtyughs);

      this.start = caves.get(rnd.getRandomFromBound(caves.size()));
      this.end = caves.get(rnd.getRandomFromBound(caves.size()));

      startEndLength = calculateStartEndLength();

      addOtyughToCaves(rnd, numOtyughs);
    } while (startEndLength < 5);
  }

  private void addPitToCaves(Randomizer rnd, int numPits) {
    if(numPits > 4) {
      numPits /= 2;
    }
    for (int i = 0; i < numPits  ; i++) {
      Location loc = caves.get(rnd.getRandomFromBound(caves.size()));

      if (loc.equals(this.start)) {
        i -= 1;
        continue;
      }

      loc.addPit();
    }
  }
}
