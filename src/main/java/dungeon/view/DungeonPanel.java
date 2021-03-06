package dungeon.view;

import org.javatuples.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.*;

import dungeon.controller.DungeonViewController;
import dungeon.controller.Features;
import dungeon.model.Location;
import dungeon.model.ReadOnlyDungeon;
import dungeon.model.SmellFactor;

public class DungeonPanel extends JPanel {

  private final ReadOnlyDungeon dungeon;
  private final File player;
  private final File blank;

  DungeonPanel(ReadOnlyDungeon dungeon) {
    this.dungeon = dungeon;
    player = new File("./dungeon-images/dungeon-images/player.png");
    blank = new File("./dungeon-images/dungeon-images/blank.png");
  }

  @Override
  public void paintComponent(Graphics g) {
    if(dungeon.gameBegin()) {

    ImageIcon finalImage;
    File cell;
    for (int i = 0; i < dungeon.getDimensionRow(); i++) {
      for (int j = 0; j < dungeon.getDimensionColumn(); j++) {
        try {
          Location current = dungeon.getLocationAt(i, j);
          JLabel loc = (JLabel) this.getClientProperty(i + " " + j);
          if (current.getIsTraversed()) {
            String imageName = getImageNameOfCell(i, j);
            cell = new File("./dungeon-images/dungeon-images/color-cells/"
                    + imageName + ".png");
            finalImage = new ImageIcon(ImageIO.read(cell));

            if (dungeon.hasPlayerAt(i, j)) {

              BufferedImage superImposedPlayer = superImpose(cell, player, 15, 10);

              if (current.getOtyugh().getQuantity() > 0) {
                superImposedPlayer = overlay(superImposedPlayer,
                        "./dungeon-images/dungeon-images/otyugh.png", 5, 5);
              }

              SmellFactor sense = dungeon.getPlayerSenseFactor();

              switch (sense) {
                case LESS_PUNGENT:
                  superImposedPlayer =
                          overlay(superImposedPlayer,
                                  "./dungeon-images/dungeon-images/stench01.png",
                                  2, 2);
                  break;
                case MORE_PUNGENT:
                  superImposedPlayer =
                          overlay(superImposedPlayer,
                                  "./dungeon-images/dungeon-images/stench02.png",
                                  2, 2);
                  break;
                case WITH_OTYUGH_DEAD:
                case WITH_OTYUGH_SAVED:
                  superImposedPlayer =
                          overlay(superImposedPlayer,
                                  "./dungeon-images/dungeon-images/otyugh.png",
                                  2, 2);
                  break;
                case NO_SMELL:
                  break;
              }
              finalImage = new ImageIcon(superImposedPlayer);
            }
            loc.setIcon(finalImage);
          }
        } catch (IOException ie) {
          ie.printStackTrace();
        }
      }
    }
  }
  }

  public String getImageNameOfCell(int row, int col) {
    List<String> dirInitials = dungeon.getLocationAt(row, col).getHasConnectionAt().stream()
            .map(dir -> dir.name().substring(0, 1)).collect(Collectors.toList());
    StringBuilder imageName = new StringBuilder();
    if (dirInitials.contains("N")) {
      imageName.append("N");
    }

    if (dirInitials.contains("S")) {
      imageName.append("S");
    }

    if (dirInitials.contains("E")) {
      imageName.append("E");
    }

    if (dirInitials.contains("W")) {
      imageName.append("W");
    }

    return imageName.toString();
  }

  /**
   * Combines two images into one image by superimposing one over the other.
   *
   * @param baseImagePath The path to the base image.
   * @param topImagePath  The path to the final image.
   * @param xOffset       The x offset for the top image.
   * @param yOffset       The y offset for the top image.
   * @return The superimposed image.
   * @throws IOException
   */
  public static BufferedImage superImpose(File baseImagePath, File topImagePath, int xOffset, int yOffset)
          throws IOException {
    BufferedImage baseImage = ImageIO.read(baseImagePath);
    BufferedImage topImage = ImageIO.read(topImagePath);

    int widthOfFinalImage = Math.max(baseImage.getWidth(), topImage.getWidth());
    int heightOfFinalImage = Math.max(baseImage.getHeight(), topImage.getHeight());

    //Creating the final image of width and height that will match the final image. The image will be RGB+Alpha
    BufferedImage finalImage = new BufferedImage(widthOfFinalImage, heightOfFinalImage,
            BufferedImage.TYPE_INT_ARGB);

    Graphics finalImageGraphics = finalImage.getGraphics();

    finalImageGraphics.drawImage(baseImage, 0, 0, null);
    finalImageGraphics.drawImage(topImage, xOffset, yOffset, null);
    return finalImage;

  }

  /**
   * Gets a BufferedImage, overlays a new image on it, and then returns the new combined image
   *
   * @param starting The base image. This is the image that needs to have another image layered over it.
   * @param fpath    The path of the image that should be superimposed i.e. the image on top of the base image.
   * @param offsetx   The x offset by which the top image should be superimposed on the base image.
   * @param offsety   The x offset by which the top image should be superimposed on the base image.
   * @return The combined image where the image in 'fpath' is superimposed on top of the image in 'starting'
   * @throws IOException Thrown when fpath is not found?
   */
  public static BufferedImage overlay(BufferedImage starting, String fpath, int offsetx, int offsety) throws IOException {
    BufferedImage overlay = ImageIO.read(new File(fpath));
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, offsetx, offsety, null);
    return combined;
  }

  public void addMouseListeners(DungeonViewController controller) {
    for(int i = 0; i < dungeon.getDimensionRow(); i++) {
      for(int j= 0; j < dungeon.getDimensionColumn(); j++) {
        JLabel label = ((JLabel) getClientProperty(i + " " + j));
        label.addMouseListener(new MyMouseAdapter(controller));
      }
    }
  }

  public void emptyTheLocations() {
    for(int i = 0; i < dungeon.getDimensionRow(); i++) {
      for(int j= 0; j < dungeon.getDimensionColumn(); j++) {
        JLabel label = ((JLabel) getClientProperty(i + " " + j));
        try {
          label.setIcon(new ImageIcon(ImageIO.read
                  (blank)));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
