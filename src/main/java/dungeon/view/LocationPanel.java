package dungeon.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

import dungeon.model.Location;
import dungeon.model.ReadOnlyDungeon;
import dungeon.model.TreasureType;

public class LocationPanel extends JPanel {
  private final ReadOnlyDungeon dungeon;
  private File ruby;
  private File diamond;
  private File sapphire;
  private File location;
  private File arrow;
  //private JLabel[][] dungeonGrid;

  LocationPanel(ReadOnlyDungeon dungeon) {
    this.dungeon = dungeon;
    setLayout(new FlowLayout());
    ruby = new File("./dungeon-images/dungeon-images/ruby.png");
    diamond = new File("./dungeon-images/dungeon-images/diamond.png");
    sapphire = new File("./dungeon-images/dungeon-images/sapphire.png");
    location = new File("./dungeon-images/dungeon-images/location.png");
    arrow = new File("./dungeon-images/dungeon-images/arrow-white.png");
  }

  @Override
  public void paintComponent(Graphics g) {
    JLabel caveEntities = ((JLabel) this.getClientProperty("baseImage"));
    if (dungeon.gameBegin()) {
      //this.removeAll();
      try {
        BufferedImage finalImage = ImageIO.read(location);
        Location playerLoc = dungeon.getPlayerLocation();

        if (playerLoc.getArrow().getQuantity() > 0) {
          finalImage = DungeonPanel.overlay(finalImage, arrow.getPath() , 40, 40);
        }

        if (playerLoc.getTreasure().getQuantity() > 0) {
          Map<TreasureType, Integer> treasures = playerLoc.getTreasure().getStones();
          if (treasures.containsKey(TreasureType.RUBY)) {
            finalImage = DungeonPanel.overlay(finalImage, ruby.getPath(), 70, 40);
          }

          if (treasures.containsKey(TreasureType.DIAMOND)) {
            finalImage = DungeonPanel.overlay(finalImage, diamond.getPath(), 110, 40);
          }

          if (treasures.containsKey(TreasureType.SAPPHIRE)) {
            finalImage = DungeonPanel.overlay(finalImage, sapphire.getPath(), 150, 40);
          }
        }

        caveEntities.setIcon(new ImageIcon(finalImage));
      } catch (IOException ioe) {
        System.out.println("There was a glitch.");
        System.exit(0);
      }
    }
  }
}
