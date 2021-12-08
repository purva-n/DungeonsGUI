package dungeon.controller;

import org.javatuples.Pair;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.swing.*;

import dungeon.model.Direction;
import dungeon.model.Dungeon;
import dungeon.view.DungeonView;
import dungeon.view.KeyBoardListener;


public class DungeonViewController extends JFrame implements Features {

  private Dungeon model;
  private DungeonView view;


  public DungeonViewController(DungeonView view, Dungeon model) {
    if (view == null || model == null) {
      throw new IllegalArgumentException("Model and view cannot be null");
    }

    this.model = model;
    this.view = view;
    configureKeyBoardListener();
  }

  private void configureKeyBoardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    keyPresses.put(KeyEvent.VK_S, () -> {
      String distance = view.addPopup();
      System.out.println(model.makePlayerShoot("S", distance));
    });

    KeyBoardListener kbd = new KeyBoardListener();
    kbd.setKeyTypedMap(keyTypes);
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);

    view.addKeyListener(kbd);
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
    view.addPanel(this);
    view.refresh();
    view.resetFocus();
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
