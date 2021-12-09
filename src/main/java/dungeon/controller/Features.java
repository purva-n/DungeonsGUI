package dungeon.controller;

import java.awt.event.KeyListener;

import dungeon.model.Direction;
import dungeon.model.SmellFactor;

public interface Features {

  void shootOtyugh(String direction, String caveDistance);

  void pickTreasure(String stone);

  void pickArrow();

  SmellFactor move(String direction);

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
