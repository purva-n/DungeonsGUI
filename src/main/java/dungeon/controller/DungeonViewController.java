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
    model.makePlayerShoot(direction, caveDistance);
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
      smell = model.movePlayer(direction);
      view.refresh();
    } catch (IllegalArgumentException | IllegalStateException e) {
      //do nothing
    }

    return smell;
  }

  @Override
  public Direction getValidDirectionOfLocationAt(int row, int col) {
    return ((Direction) model.getValidDirectionOfLocationAt(row, col));
  }

  @Override
  public void startGame() {
    model.startQuest();
    System.out.println("Add panel");
    view.addPanel(this);
    System.out.println("Added panel");

    System.out.println("view refresh");
    view.refresh();
    System.out.println("View refreshed");
  }

  @Override
  public void playGame() {
    view.setFeatures(this, this);
    view.makeVisible(true);
  }

  @Override
  public void restartGame() {

  }

  @Override
  public void processRows() {
    Integer row = validateIntegerNumber(view.getRows());
    if (row == null) {
      // view.popup
      view.clearRows();
      view.resetRowsFocus();
    } else {
      model.setRow(row);
    }
  }

  @Override
  public void processColumns() {
    Integer col = validateIntegerNumber(view.getColumns());
    if (col == null) {
      // view.popup
      view.clearColumns();
      view.resetColumnsFocus();
    } else {
      model.setColumn(col);
    }
  }

  @Override
  public void processInterconnectivity() {
    Integer interconnectivity = validateIntegerNumber(view.getInterconnectivity());
    if (interconnectivity == null) {
      // view.popup
      view.clearInterconnectivity();
      view.resetInterconnectivityFocus();
    } else {
      model.setInterconnectivity(interconnectivity);
    }
  }

  @Override
  public void processIsWrap() {
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

  @Override
  public void processTreasurePercent() {
    Integer treasurePercent = validateIntegerNumber(view.getTreasurePercentage());
    if (treasurePercent == null) {
      // view.popup
      view.clearTreasurePercent();
      view.resetTreasureFocus();
    } else {
      model.setTreasurePercent(treasurePercent);
    }
  }

  @Override
  public void processNumOtyughs() {
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

      } catch (IllegalArgumentException ex) {
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
        view.errorPopup(ex.getMessage());
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

      if(smellFactor == SmellFactor.WITH_OTYUGH_DEAD) {
       view.makeVisible(false);
      }
    }
  }
}
