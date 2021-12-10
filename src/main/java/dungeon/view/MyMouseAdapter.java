package dungeon.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.*;

import dungeon.controller.DungeonViewController;
import dungeon.model.Direction;
import dungeon.model.SmellFactor;

public class MyMouseAdapter extends MouseAdapter {

  private final DungeonViewController listener;

  MyMouseAdapter(DungeonViewController c) {
    listener = c;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);

    String label = ((JLabel) e.getSource()).getName();
    String[] indices = label.split(" ");
    List<Integer> rowCol = Arrays.stream(indices)
            .map(Integer::parseInt).collect(Collectors.toList());

    int row = rowCol.get(0);
    int col = rowCol.get(1);
    Direction d = listener.getValidDirectionOfLocationAt(row, col);
    String dirInitial = d.name().substring(0,1);
    SmellFactor smell = listener.move(dirInitial);
    if(smell == SmellFactor.WITH_OTYUGH_DEAD) {
      listener.afterPlayerDead();
    } else if (smell == SmellFactor.WITH_OTYUGH_SAVED) {
      listener.afterPlayerSaved();
    }
  }
}
