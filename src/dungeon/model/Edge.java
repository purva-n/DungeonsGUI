package dungeon.model;

/**
 * Class to define the possible edges inside the dungeon for building the dungeon paths.
 * Each Edge is comprised of 2 {@link Location}s.
 */
class Edge {
  private Location l1;
  private Location l2;

  public Edge(Location l1, Location l2) {
    this.l1 = l1;
    this.l2 = l2;
  }

  Location getL1() {
    return l1;
  }

  Location getL2() {
    return l2;
  }
}
