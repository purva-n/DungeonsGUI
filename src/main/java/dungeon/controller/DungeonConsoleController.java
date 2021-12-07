package dungeon.controller;

import org.javatuples.Pair;

import dungeon.model.Dungeon;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import dungeon.model.SmellFactor;
import dungeon.model.WindFactor;
import random.Randomizer;

/**
 * Class that represents the Controller of the Dungeon Game.
 * It gives a way for the user to play the Text Based adventure game by giving inputs to the game.
 * It waits for the user to input the choice to move ahead and progress in the Game.
 * It responds to the input given by the user by passing the input to the model and presenting the\
 * change in the model state back to the user.
 */
public class DungeonConsoleController implements DungeonController {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructs the Controller for accepting inputs and recording output.
   *
   * @param in  Readable form of input from console.
   * @param out Appendable form where the effects to the model due to the input are presented back
   *            to the user on the console.
   */
  public DungeonConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    this.scan = new Scanner(in);
  }

  /**
   * Method to begin playing the Text based adventure Game.
   *
   * @param rnd         random number generator.
   * @param dungeonGame Dungeon model to which the controller interacts with.
   * @throws IllegalArgumentException thrown when arguments to the method are Illegal.
   * @throws IOException              thrown when scanner or input interface fails to get input.
   */
  @Override
  public void playGame(Randomizer rnd, Dungeon dungeonGame) throws IllegalArgumentException,
          IOException {
    if (dungeonGame == null || rnd == null) {
      throw new IllegalArgumentException("Invalid model.");
    }

    showSmellFactor(dungeonGame.startQuest().getValue0().getSmellFactor());
    loop:
    while (!dungeonGame.gameOver()) {
      out.append(dungeonGame.toString()).append("\n");
      out.append("Doors lead to: ");
      out.append(dungeonGame.getPlayerMoves().toString()).append("\n\n");

      showSmellFactor(dungeonGame.getPlayerSenseFactor().getValue0().getSmellFactor());
      showWindFactor(dungeonGame.getPlayerSenseFactor().getValue1().getWindFactor());

      out.append(dungeonGame.getPlayerInfo()).append("\n");
      out.append("Move Pick Shoot ? ( M/P/S )\n");
      String choice = scan.next();

      switch (choice) {
        case "M":
          out.append("Where to N/E/W/S ? ");
          String direction = scan.next();
          move(dungeonGame, direction, out, rnd);
          break;
        case "P":
          out.append("What ? ");
          String pickup = scan.next();

          switch (pickup) {
            case "arrow":
              dungeonGame.makePlayerCollectArrow();
              out.append("You collected arrow(s).\n");
              break;
            case "ruby":
            case "diamond":
            case "sapphire":
              try {
                dungeonGame.makePlayerCollectTreasure(pickup);
                out.append("You collected ").append(pickup).append("(s)\n");
              } catch (IllegalStateException ise) {
                out.append("This treasure is not found here.\n");
              } catch (NoSuchElementException nse) {
                out.append("No treasure found here.\n");
              } catch (IllegalArgumentException iae) {
                out.append(iae.getMessage()).append("\n");
              }
              break;
            default:
              out.append("There is no item like that.\n");
          }
          break;
        case "S":
          out.append("Where to N/E/W/S ? ");
          direction = scan.next();
          out.append("How many caves? 1 - 5 ");
          String distance = scan.next();
          int effect;
          try {
            effect = dungeonGame.makePlayerShoot(direction, distance);
          } catch (IllegalArgumentException ex) {
            effect = 0;
          }
          switch (effect) {
            case -1:
              out.append("You are out of arrows. Explore to collect arrows to shoot.");
              break;
            case 0:
              out.append("You shoot an arrow in the darkness.\n");
              break;
            case 1:
              out.append("You hear a howling sound.\n");
              break;
            case 2:
              out.append("You hear a loud howling sound and a thump to the ground.\n");
              break;
            default:
              out.append("Try again.\n");
          }
          break;
        case "q":

          break loop;
        default:
          out.append("No such option available. Try again.\n");
          break;
      }
    }

    if (dungeonGame.getPlayerLocation().equals(dungeonGame.getEndLoc())
            && dungeonGame.playerIsAlive()) {
      out.append("\n****Hurray!!!! You win!****\n");
    }

    out.append("Player collected:\n");
    out.append(dungeonGame.getPlayersCollectedTreasure().toString()).append("\n");
  }

  private void move(Dungeon dungeonGame, String direction, Appendable out, Randomizer rnd)
          throws IOException {
    Pair<SmellFactor, WindFactor> senseFactor = new Pair<>(SmellFactor.NO_SMELL, WindFactor.NO_WIND);
    try {
      senseFactor = dungeonGame.movePlayer(direction);

      showSmellFactor(senseFactor.getValue0().getSmellFactor());
      showWindFactor(senseFactor.getValue1().getWindFactor());
    } catch (IllegalArgumentException ex) {
      out.append("Didn't get you? Try from available directions.\n");
    }
  }

  private void showWindFactor(int windFactor) throws IOException {
    switch (windFactor) {
      case 0:
        break;
      case 1:
        out.append("You feel a light breeze around.\n");
        break;
      case 2:
        out.append("You feel a powerful wind around. Doesn't feel good\n");
        break;
      default:
        out.append("There was a glitch.\n");
    }
  }

  private void showSmellFactor(int smellFactor) throws IOException {
    switch (smellFactor) {
      case 0:
        break;
      case 1:
        out.append("You smell something fishy nearby.").append("\n");
        break;
      case 2:
        out.append("You smell something really terrible now!").append("\n");
        break;
      case 3:
        out.append("Phew! That was a close call with the Giant Otyugh.").append("\n");
        break;
      case 4:
        out.append("Chomp, chomp, chomp! You were eaten. Game Over!").append("\n");
        break;
      default:
        out.append("There was a glitch.\n");
    }
  }
}
