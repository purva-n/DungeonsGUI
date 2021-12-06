package dungeon.controller;

import dungeon.controller.DungeonController;

public interface Features {

  void shootOtyugh(String direction, String caveDistance);

  void pickTreasure(String stone);

  void pickArrow();

  void move(String direction);

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
