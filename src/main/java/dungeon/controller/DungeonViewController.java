package dungeon.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.NoSuchElementException;

import javax.swing.*;

import dungeon.model.Direction;
import dungeon.model.Dungeon;
import dungeon.model.SmellFactor;
import dungeon.view.DungeonView;

import static java.awt.event.KeyEvent.VK_P;

public class DungeonViewController extends JFrame implements Features, KeyListener {

  private final Dungeon model;
  private final DungeonView view;
  boolean pick = false;
  boolean shoot = false;
  boolean directionSet = false;
  String direction = "Z";

  public DungeonViewController(DungeonView view, Dungeon model) {
    if (view == null || model == null) {
      throw new IllegalArgumentException("Model and view cannot be null");
    }

    this.model = model;
    this.view = view;
  }

  @Override
  public void shootOtyugh(String direction, String caveDistance) {
    switch (model.makePlayerShoot(direction, caveDistance)) {
      case OUT_OF_ARROWS:
        view.setPlayerAction("You're out of arrows. Collect more to continue");
        break;
      case DIDNT_HIT:
        view.setPlayerAction("You hit an arrow into the darkness");
        break;
      case INJURED:
        view.setPlayerAction("You hear a howling sound.");
        break;
      case KILLED:
        view.setPlayerAction("You hear a loud howling sound and a thump to the ground.");
        break;
      default:
        view.setPlayerAction("Something went wrong.");
    }
  }

  @Override
  public void pickTreasure(String stone) {
    model.makePlayerCollectTreasure(stone);
  }

  @Override
  public void pickArrow() {
    model.makePlayerCollectArrow();
  }

  @Override
  public SmellFactor move(String direction) {
    SmellFactor smell = SmellFactor.NO_SMELL;
    try {
      view.setPlayerAction("");
      smell = model.movePlayer(direction);
      if(model.gameOver() && model.playerIsAlive()) {
        view.setPlayerAction("You won!!! Hurrah");
        view.removeDungeonPanelListeners();
      }
      view.refresh();
    } catch (IllegalArgumentException | IllegalStateException e) {
      //do nothing
      view.setPlayerAction("No door there");
    }

    return smell;
  }

  @Override
  public Direction getValidDirectionOfLocationAt(int row, int col) {
    return ((Direction) model.getValidDirectionOfLocationAt(row, col));
  }

  @Override
  public void startGame() {
    view.removeDungeonPanel();
    view.setPlayerAction("");
    model.startQuest();
    System.out.println("Add panel");
    view.addPanel(this);
    System.out.println("Added panel");

    System.out.println("view refresh");
    view.refresh();
    view.setFocus(true);
    System.out.println("View refreshed");
  }

  @Override
  public void playGame() {
    view.setFeatures(this, this);
    view.makeVisible(true);
  }

  @Override
  public void restartGame() {
    model.setRestart();
    view.clearDungeonPanel(this);
    model.startQuest();
    view.setFocus(true);
    view.refresh();
  }

  @Override
  public void processGameSettings() {
    processRows();
    processColumns();
    processInterconnectivity();
    processIsWrap();
    processTreasurePercent();
    processNumOtyughs();
  }

  private void processRows() {
    Integer row = validateIntegerNumber(view.getRows());
    if (row == null) {
      // view.popup
      view.clearRows();
      view.resetRowsFocus();
    } else {
      model.setRow(row);
    }
  }

  private void processColumns() {
    Integer col = validateIntegerNumber(view.getColumns());
    if (col == null) {
      // view.popup
      view.clearColumns();
      view.resetColumnsFocus();
    } else {
      model.setColumn(col);
    }
  }


  private void processInterconnectivity() {
    Integer interconnectivity = validateIntegerNumber(view.getInterconnectivity());
    if (interconnectivity == null) {
      // view.popup
      view.clearInterconnectivity();
      view.resetInterconnectivityFocus();
    } else {
      model.setInterconnectivity(interconnectivity);
    }
  }

  private void processIsWrap() {
    String isWrap = view.getIsWrap();
    if (isWrap.equals("Y") || isWrap.equals("y")) {
      model.setIsWrap(true);
    } else if (isWrap.equals("N") || isWrap.equals("n")) {
      model.setIsWrap(false);
    } else {
      // view.popup
      view.clearIsWrap();
      view.resetIsWrapFocus();
    }
  }

  private void processTreasurePercent() {
    Integer treasurePercent = validateIntegerNumber(view.getTreasurePercentage());
    if (treasurePercent == null || treasurePercent > 100) {
      // view.popup
      view.clearTreasurePercent();
      view.resetTreasureFocus();
    } else {
      model.setTreasurePercent(treasurePercent);
    }
  }


  private void processNumOtyughs() {
    Integer numOtyughs = validateIntegerNumber(view.getNumOtyughs());
    if (numOtyughs == null) {
      // view.popup
      view.clearNumOtyughs();
      view.resetNumOtyughsFocus();
    } else {
      model.setNumOtyughs(numOtyughs);
    }
  }

  private Integer validateIntegerNumber(String input) {
    int inputNum;
    try {
      inputNum = Integer.parseInt(input);
      if (inputNum < 0) {
        throw new IllegalArgumentException();
      }
    } catch (NoSuchElementException | IllegalArgumentException | NullPointerException e) {
      return null;
    }
    return inputNum;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (directionSet) {
      try {
        switch (e.getKeyChar()) {
          case '1':
            shootOtyugh(direction, "1");
            break;
          case '2':
            shootOtyugh(direction, "2");
            break;
          case '3':
            shootOtyugh(direction, "3");
            break;
          case '4':
            shootOtyugh(direction, "4");
            break;
          case '5':
            shootOtyugh(direction, "5");
            break;
          default:
            //do nothing
        }

      } catch (IllegalArgumentException | NoSuchElementException ex) {
        // do nothing
      }
      directionSet = false;
      shoot = false;
      view.refresh();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == VK_P) {
      pick = true;
    }

    if (e.getKeyCode() == KeyEvent.VK_S) {
      shoot = true;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    SmellFactor smellFactor = SmellFactor.NO_SMELL;
    if (pick) {
      try {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_A:
            pickArrow();
            break;
          case KeyEvent.VK_D:
            pickTreasure("diamond");
            break;
          case KeyEvent.VK_R:
            pickTreasure("ruby");
            break;
          case KeyEvent.VK_E:
            pickTreasure("sapphire");
            break;
          default:
            //do nothing
        }
      } catch (IllegalStateException ex) {
        view.setPlayerAction(ex.getMessage());
      }

      pick = false;
      view.refresh();
    }

    if (shoot) {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_DOWN:
          direction = Direction.SOUTH.name().substring(0, 1);
          directionSet = true;
          break;
        case KeyEvent.VK_LEFT:
          direction = Direction.WEST.name().substring(0, 1);
          directionSet = true;
          break;
        case KeyEvent.VK_RIGHT:
          direction = Direction.EAST.name().substring(0, 1);
          directionSet = true;
          break;
        case KeyEvent.VK_UP:
          direction = Direction.NORTH.name().substring(0, 1);
          directionSet = true;
          break;
        default:
          direction = Direction.ZERO.name().substring(0, 1);
          directionSet = false;
      }
    } else {
      if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        smellFactor = move("S");
      }

      if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        smellFactor = move("W");
      }

      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        smellFactor = move("E");
      }

      if (e.getKeyCode() == KeyEvent.VK_UP) {
        smellFactor = move("N");
      }

      if (smellFactor == SmellFactor.WITH_OTYUGH_DEAD) {
        //view.makeVisible(false);
        afterPlayerDead();
      } else if (smellFactor == SmellFactor.WITH_OTYUGH_SAVED) {
        afterPlayerSaved();
      }
    }
  }

  public void afterPlayerDead() {
    view.setFocus(false);
    view.setPlayerAction("Chomp! Chomp! Chomp! You are eaten by Otyugh. Game Over.");
    view.removeDungeonPanelListeners();
  }

  public void afterPlayerSaved() {
    view.setPlayerAction("Phew! That was a close save. Run away quick");
  }
}
