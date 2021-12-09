package dungeon.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.NoSuchElementException;

import javax.swing.*;

import dungeon.model.Direction;
import dungeon.model.Dungeon;
import dungeon.view.DungeonView;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_P;


public class DungeonViewController extends JFrame implements Features, KeyListener {

  private final Dungeon model;
  private final DungeonView view;
  boolean pick = false;
  boolean shoot = false;

  public DungeonViewController(DungeonView view, Dungeon model) {
    if (view == null || model == null) {
      throw new IllegalArgumentException("Model and view cannot be null");
    }

    this.model = model;
    this.view = view;
  }

  @Override
  public void shootOtyugh(String direction, String caveDistance) {

  }

  @Override
  public void pickTreasure(String stone) {

  }

  @Override
  public void pickArrow() {

  }

  @Override
  public void move(String direction) {
    try {
      model.movePlayer(direction);
      view.refresh();
    } catch (IllegalArgumentException | IllegalStateException e) {
      //do nothing
    }
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
    view.makeVisible();
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
    if(isWrap.equals("Y") || isWrap.equals("y")) {
      model.setIsWrap(true);
    } else if(isWrap.equals("N") || isWrap.equals("n")) {
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

  }

  @Override
  public void keyPressed(KeyEvent e) {
    System.out.println(e.getKeyCode());

    if(e.getKeyCode() == VK_P) {
      pick = true;
    }

    if(e.getKeyCode() == KeyEvent.VK_S) {
      shoot = true;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    System.out.println(e.getKeyCode());

    if(pick) {
      switch (e.getKeyCode()) {
        case VK_A:
          model.makePlayerCollectArrow();
          break;
        case KeyEvent.VK_D:
          model.makePlayerCollectTreasure("diamond");
          break;
        case KeyEvent.VK_R:
          model.makePlayerCollectTreasure("ruby");
          break;
        case KeyEvent.VK_S:
          model.makePlayerCollectTreasure("sapphire");
          break;
        default:
          //do nothing
      }

      pick = false;
      view.refresh();
    }


    if(e.getKeyCode() == KeyEvent.VK_DOWN) {
      move("S");
    }

    if(e.getKeyCode() == KeyEvent.VK_LEFT) {
      move("W");
    }

    if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
      move("E");
    }

    if(e.getKeyCode() == KeyEvent.VK_UP) {
      move("N");
    }
  }
}
