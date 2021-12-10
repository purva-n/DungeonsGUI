package dungeon.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import random.Randomizer;
import random.TrueRandom;

/**
 * Class that defines a way to provide a static constructed dungeon to {@link DungeonGame}
 * for testing.
 */
public class DungeonStatic extends AbstractDungeon {
  private final int row;
  private final int col;
  private final int interconnectivity;
  private final Location start;
  private final Location end;
  private final boolean isWrapping;
  private static List<List<Location>> dungeon;
  private final Player player;
  private Randomizer rnd;

  /**
   * Constructor to initialise the initial configurations of the static dungeon.
   *
   * @param row               number of rows for the static dungeon.
   * @param col               number of columns for the static dungeon.
   * @param interconnectivity additional paths inside the dungeon across {@link Location}s.
   * @param isWrap            whether the dungeon is a wrapping dungeon.
   */
  public DungeonStatic(int row, int col, int interconnectivity, boolean isWrap) {
    this.row = row;
    this.col = col;
    this.interconnectivity = interconnectivity;
    isWrapping = isWrap;
    player = new PlayerImpl();
    rnd = new TrueRandom();

    dungeon = new ArrayList<>();
    for (int i = 0; i < row; i++) {
      dungeon.add(new ArrayList<>());
    }

    dungeon.get(0).add(new Location(0, 0));
    dungeon.get(0).add(new Location(0, 1));
    dungeon.get(0).add(new Location(0, 2));
    dungeon.get(0).add(new Location(0, 3));
    dungeon.get(1).add(new Location(1, 0));
    dungeon.get(1).add(new Location(1, 1));
    dungeon.get(1).add(new Location(1, 2));
    dungeon.get(1).add(new Location(1, 3));
    dungeon.get(2).add(new Location(2, 0));
    dungeon.get(2).add(new Location(2, 1));
    dungeon.get(2).add(new Location(2, 2));
    dungeon.get(2).add(new Location(2, 3));

    dungeon.get(0).get(0).updateConnection(dungeon.get(0).get(1), isWrap, row, col);
    dungeon.get(0).get(1).updateConnection(dungeon.get(0).get(0), isWrap, row, col);

    dungeon.get(1).get(1).updateConnection(dungeon.get(0).get(1), isWrap, row, col);
    dungeon.get(0).get(1).updateConnection(dungeon.get(1).get(1), isWrap, row, col);

    dungeon.get(1).get(0).updateConnection(dungeon.get(1).get(1), isWrap, row, col);
    dungeon.get(1).get(1).updateConnection(dungeon.get(1).get(0), isWrap, row, col);

    dungeon.get(1).get(0).updateConnection(dungeon.get(2).get(0), isWrap, row, col);
    dungeon.get(2).get(0).updateConnection(dungeon.get(1).get(0), isWrap, row, col);

    dungeon.get(1).get(1).updateConnection(dungeon.get(2).get(1), isWrap, row, col);
    dungeon.get(2).get(1).updateConnection(dungeon.get(1).get(1), isWrap, row, col);

    dungeon.get(1).get(1).updateConnection(dungeon.get(1).get(2), isWrap, row, col);
    dungeon.get(1).get(2).updateConnection(dungeon.get(1).get(1), isWrap, row, col);

    dungeon.get(0).get(2).updateConnection(dungeon.get(1).get(2), isWrap, row, col);
    dungeon.get(1).get(2).updateConnection(dungeon.get(0).get(2), isWrap, row, col);

    dungeon.get(1).get(2).updateConnection(dungeon.get(2).get(2), isWrap, row, col);
    dungeon.get(2).get(2).updateConnection(dungeon.get(1).get(2), isWrap, row, col);

    dungeon.get(1).get(2).updateConnection(dungeon.get(1).get(3), isWrap, row, col);
    dungeon.get(1).get(3).updateConnection(dungeon.get(1).get(2), isWrap, row, col);

    dungeon.get(0).get(2).updateConnection(dungeon.get(0).get(3), isWrap, row, col);
    dungeon.get(0).get(3).updateConnection(dungeon.get(0).get(2), isWrap, row, col);

    dungeon.get(0).get(3).updateConnection(dungeon.get(1).get(3), isWrap, row, col);
    dungeon.get(1).get(3).updateConnection(dungeon.get(0).get(3), isWrap, row, col);

    dungeon.get(1).get(3).updateConnection(dungeon.get(2).get(3), isWrap, row, col);
    dungeon.get(2).get(3).updateConnection(dungeon.get(1).get(3), isWrap, row, col);

    dungeon.get(2).get(1).updateConnection(dungeon.get(2).get(2), isWrap, row, col);
    dungeon.get(2).get(2).updateConnection(dungeon.get(2).get(1), isWrap, row, col);

    if (isWrap) {
      dungeon.get(1).get(0).updateConnection(dungeon.get(1).get(3), isWrap, row, col);
      dungeon.get(1).get(3).updateConnection(dungeon.get(1).get(0), isWrap, row, col);

      dungeon.get(0).get(2).updateConnection(dungeon.get(2).get(2), isWrap, row, col);
      dungeon.get(2).get(2).updateConnection(dungeon.get(0).get(2), isWrap, row, col);
    }

    start = dungeon.get(0).get(0);
    end = dungeon.get(2).get(3);
  }

  public static List<List<Location>> getDungeon() {
    return dungeon;
  }

  @Override
  public boolean isWrapping() {
    return isWrapping;
  }

  @Override
  public int getDimensionRow() {
    return row;
  }

  @Override
  public int getDimensionColumn() {
    return col;
  }

  @Override
  public int getInterconnectivity() {
    return interconnectivity;
  }

  @Override
  public SmellFactor startQuest() {
    start.setPlayer(player, rnd);
    return player.setLocation(start, rnd);
  }

  @Override
  public String getPlayerInfo() {
    return player.toString();
  }

  @Override
  public ShootStatus makePlayerShoot(String direction, String distanceToShoot) {
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

  @Override
  public Player getPlayer() {
    return new PlayerImpl(rnd, player);
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

  @Override
  public Location getLocationAt(int row, int column) {
    return new Location(dungeon.get(row).get(column));
  }

  @Override
  public boolean hasPlayerAt(int row, int col) {
    return true;
  }

  @Override
  public boolean gameBegin() {
    return false;
  }

  @Override
  public Location getStartLoc() {
    return start;
  }

  @Override
  public Location getEndLoc() {
    return end;
  }
}
