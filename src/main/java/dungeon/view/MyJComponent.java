package dungeon.view;

import java.awt.*;

import javax.swing.JComponent;

import dungeon.model.ReadOnlyDungeon;

public class MyJComponent extends JComponent {

  private ReadOnlyDungeon dungeon;

  public MyJComponent(ReadOnlyDungeon dungeon) {
    this.dungeon = dungeon;
  }


  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.setColor(Color.lightGray);
    g.fillRect(30, 30, 100 * dungeon.getDimensionColumn(),
            100 * dungeon.getDimensionRow());
  }
}
