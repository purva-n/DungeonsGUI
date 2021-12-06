package dungeon.controller;

import java.util.NoSuchElementException;

import javax.swing.*;

import dungeon.model.Dungeon;
import dungeon.view.DungeonView;

public class DungeonViewController extends JFrame implements Features {

  private Dungeon model;
  private DungeonView view;


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

  }

  @Override
  public void startGame() {
    model.startQuest();
    view.addPanel();
    view.refresh();
  }

  @Override
  public void playGame() {
    view.setFeatures(this);
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
    Integer treasurePercent = validateIntegerNumber(view.getTreasurePerecentage());
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

}
