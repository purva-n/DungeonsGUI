package dungeon.controller;

import dungeon.model.Dungeon;
import java.io.IOException;
import random.Randomizer;

/**
 * Interface to define functionalities of the Game Controller.
 */
public interface DungeonController {

  /**
   * Method to begin playing the Text based adventure Game.
   *
   * @param rnd         random number generator.
   * @param dungeonGame Dungeon model to which the controller interacts with.
   * @throws IllegalArgumentException thrown when arguments to the method are Illegal.
   * @throws IOException              thrown when scanner or input interface fails to get input.
   */
  void playGame(Randomizer rnd, Dungeon dungeonGame) throws IllegalArgumentException, IOException;
}
