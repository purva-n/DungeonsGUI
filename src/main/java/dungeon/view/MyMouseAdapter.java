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

public class MyMouseAdapter extends MouseAdapter {

  private final DungeonViewController listener;
  private final DungeonView view;

  MyMouseAdapter(DungeonViewController c, DungeonView dungeonSwingView) {
    listener = c;
    view = dungeonSwingView;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);

    String label = ((JLabel) e.getSource()).getName();
    String[] indices = label.split(" ");
    List<Integer> rowCol = Arrays.stream(indices)
            .map(Integer::parseInt).collect(Collectors.toList());

    int row = rowCol.get(0); int col = rowCol.get(1);
    Direction d = listener.getValidDirectionOfLocationAt(row, col);
    String dirInitial = d.name().substring(0,1);

    if(!dirInitial.equals("Z")) {
      listener.move(dirInitial);
      String imageName = view.getImageNameOfCell(row, col);
      try {
        ((JLabel) e.getSource()).setIcon(new ImageIcon(ImageIO.read(
                new File("./dungeon-images/dungeon-images/color-cells/"
                        + imageName + ".png"))));
      } catch (IOException ie) {
        ie.printStackTrace();
      }

    }
  }
}
