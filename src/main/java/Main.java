import dungeon.controller.DungeonViewController;
import dungeon.model.Dungeon;
import dungeon.model.DungeonGame;
import dungeon.model.Player;
import java.io.IOException;

import dungeon.view.DungeonSwingView;
import dungeon.view.DungeonView;
import dungeon.controller.Features;
import random.Randomizer;
import random.TrueRandom;

/**
 * Class to execute the game play played by the {@link Player} inside the {@link Dungeon}.
 */
public class Main {
  private static final Randomizer rnd = new TrueRandom();

  /**
   * Method which marks the beginning of the execution of the program.
   * Reference at which the game play begins.
   *
   * @param args USer inputs to the java / jar program.
   */
  public static void main(String[] args) throws IOException {

    Dungeon dungeonQuest = new DungeonGame(rnd);

    // Create the model
    DungeonView view = new DungeonSwingView(dungeonQuest);
    Dungeon model = new DungeonGame(rnd);

    // Create the controller with the model
    Features controller = new DungeonViewController(view, model);
    controller.playGame();
  }
}
