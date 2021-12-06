package dungeon.view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import dungeon.model.ReadOnlyDungeon;

public class DungeonPanel extends JPanel {

  private ReadOnlyDungeon dungeon;

  DungeonPanel(ReadOnlyDungeon dungeon) {
    this.dungeon = dungeon;
    //setLayout(new GridLayout(dungeon.getDimensionRow(),dungeon.getDimensionColumn()));
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
}
