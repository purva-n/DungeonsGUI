package dungeon.controller;

import dungeon.model.Direction;
import dungeon.model.ShootStatus;
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

  void processGameSettings();

  void afterPlayerDead();

  void afterPlayerSaved();
}
