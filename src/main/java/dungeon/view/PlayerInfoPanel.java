package dungeon.view;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

import dungeon.model.ReadOnlyDungeon;
import dungeon.model.TreasureType;

public class PlayerInfoPanel extends JPanel {

  private final ReadOnlyDungeon dungeon;
  private final File diamond;
  private final File ruby;
  private final File sapphire;
  private final File arrow;
  private final File whitecanvas;
  private final File[] fileArray;

  public PlayerInfoPanel(ReadOnlyDungeon dungeon) {
    this.dungeon = dungeon;
    ruby = new File("./dungeon-images/dungeon-images/ruby.png");
    diamond = new File("./dungeon-images/dungeon-images/diamond.png");
    sapphire = new File("./dungeon-images/dungeon-images/sapphire.png");
    whitecanvas = new File("./dungeon-images/dungeon-images/whitecanvas.png");
    arrow = new File("./dungeon-images/dungeon-images/white-arrow.png");

    fileArray = new File[]{
            arrow,
            ruby,
            diamond,
            sapphire
    };
  }

  @Override
  public void paintComponent(Graphics g) {
    if (dungeon.gameBegin()) {
      Map<TreasureType, Integer> treasures = dungeon.getPlayersCollectedTreasure();

      JLabel arrowCount = ((JLabel) getClientProperty("01"));
      arrowCount.setText(String.valueOf(dungeon.getPlayerArrowCount()));

      JLabel rubyCount = ((JLabel) getClientProperty("11"));
      if (treasures.containsKey(TreasureType.RUBY)) {
        rubyCount.setText(String.valueOf(treasures.get(TreasureType.RUBY)));
      } else {
        rubyCount.setText("0");
      }

      JLabel diamondCount = ((JLabel) getClientProperty("21"));
      if (treasures.containsKey(TreasureType.DIAMOND)) {
        diamondCount.setText(String.valueOf(treasures.get(TreasureType.DIAMOND)));
      } else {
        diamondCount.setText("0");
      }

      JLabel sapphireCount = ((JLabel) getClientProperty("31"));
      if (treasures.containsKey(TreasureType.SAPPHIRE)) {
        sapphireCount.setText(String.valueOf(treasures.get(TreasureType.SAPPHIRE)));
      } else {
        sapphireCount.setText("0");
      }

    }
  }
}
