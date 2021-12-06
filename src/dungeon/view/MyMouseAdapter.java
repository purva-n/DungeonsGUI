package dungeon.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import dungeon.controller.DungeonViewController;
import dungeon.model.ReadOnlyDungeon;

public class MyMouseAdapter extends MouseAdapter {

  private DungeonViewController controller;

  MyMouseAdapter(DungeonViewController c) {
    controller = c;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);

  }
}
