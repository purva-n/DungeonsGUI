package dungeon.model;

/**
 * Enum class which defines the possible directions for {@link Player}.
 * These directions are for the Player to move inside the {@link Dungeon}.
 * The movement is made through the {@link Location} inside the constructed Dungeon.
 */
public enum Direction {
  NORTH(-1, 0),
  EAST(0, 1),
  SOUTH(1, 0),
  WEST(0, -1),
  NONE(0, 0);

  private final int row;
  private final int column;

  Direction(int row, int column) {
    this.row = row;
    this.column = column;
  }

  /**
   * Method to get the row delta.
   *
   * @return integer value of the row.
   */
  public int getRow() {
    return row;
  }

  /**
   * Method to get the column delta.
   *
   * @return integer value of the column.
   */
  public int getColumn() {
    return column;
  }

  /**
   * Method to get the opposite direction of the referenced direction.
   *
   * @return Direction opposite to the given.
   */
  public Direction getOpposite() {
    switch (this.name()) {
      case "NORTH":
        return SOUTH;
      case "SOUTH":
        return NORTH;
      case "EAST":
        return WEST;
      case "WEST":
        return EAST;
      default:
        return NONE;
    }
  }
}
