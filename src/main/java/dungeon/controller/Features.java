package dungeon.controller;

import dungeon.controller.DungeonController;
import dungeon.model.Direction;

public interface Features {

  void shootOtyugh(String direction, String caveDistance);

  void pickTreasure(String stone);

  void pickArrow();

  void move(String direction);

  Direction getValidDirectionOfLocationAt(int row, int col);

  void startGame();

  void playGame();

  void restartGame();

  void processRows();

  void processColumns();

  void processInterconnectivity();

  void processIsWrap();

  void processTreasurePercent();

  void processNumOtyughs();
}
