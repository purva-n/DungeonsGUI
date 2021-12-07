package dungeon.view;

import java.awt.*;

import javax.swing.*;

import dungeon.model.ReadOnlyDungeon;

public class DungeonPanel extends JPanel {

  private final ReadOnlyDungeon dungeon;
  //private JLabel[][] dungeonGrid;

  DungeonPanel(ReadOnlyDungeon dungeon) {
    this.dungeon = dungeon;
    //setLayout(new GridLayout(dungeon.getDimensionRow(),dungeon.getDimensionColumn()));
  }

  @Override
  public void paintComponent(Graphics g) {

  }
}
