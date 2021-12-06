package dungeon.view;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.*;

import dungeon.controller.Features;
import dungeon.model.ReadOnlyDungeon;

public class DungeonSwingView extends JFrame implements DungeonView {

  private JMenuBar menubar;
  private JMenu menu;
  private JButton startQuest;
  private JButton quit;
  private JButton restartQuest;

  private JButton rowsOkay;
  private JButton columnsOkay;
  private JButton interconnectivityOkay;
  private JButton isWrapOkay;
  private JButton numOtyughsOkay;
  private JButton treasurePercentOkay;

  private DungeonPanel panel;
  private JTextField rows;
  private JTextField columns;
  private JTextField interconnectivity;
  private JTextField isWrap;
  private JTextField numOtyughs;
  private JTextField treasureArrowPercent;
  private ReadOnlyDungeon dungeon;

  public DungeonSwingView(ReadOnlyDungeon dungeon) {

    setSize(300, 400);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    startQuest = new JButton("Start");
    startQuest.setActionCommand("Start the game");

    quit = new JButton("Quit");
    quit.setActionCommand("Quit game");

    restartQuest = new JButton("Restart");
    restartQuest.setActionCommand("Restart the game");

    menubar = new JMenuBar();
    menu = new JMenu("Game Settings");

    rowsOkay = new JButton("OK");
    columnsOkay = new JButton("OK");
    interconnectivityOkay = new JButton("OK");
    isWrapOkay = new JButton("OK");
    numOtyughsOkay = new JButton("OK");
    treasurePercentOkay = new JButton("OK");

    JMenu rowsMenu = new JMenu("Rows");
    rows = new JTextField();
    rowsMenu.add(rows);
    rowsMenu.add(rowsOkay);
    menu.add(rowsMenu);

    JMenu columnsMenu = new JMenu("Columns:");
    columns = new JTextField();
    columnsMenu.add(columns);
    columnsMenu.add(columnsOkay);
    menu.add(columnsMenu);

    JMenu interconnectivityMenu = new JMenu("Interconnectivity:");
    interconnectivity = new JTextField();
    interconnectivityMenu.add(interconnectivity);
    interconnectivityMenu.add(interconnectivityOkay);
    menu.add(interconnectivityMenu);

    JMenu isWrapMenu = new JMenu("Wrapping:");
    isWrap = new JTextField();
    isWrapMenu.add(isWrap);
    isWrapMenu.add(isWrapOkay);
    menu.add(isWrapMenu);

    JMenu treasureArrowPercentMenu = new JMenu("Treasure Percent:");
    treasureArrowPercent = new JTextField();
    treasureArrowPercentMenu.add(treasureArrowPercent);
    treasureArrowPercentMenu.add(treasurePercentOkay);
    menu.add(treasureArrowPercentMenu);

    JMenu otyughNumbers = new JMenu("Otyugh(s):");
    numOtyughs = new JTextField();
    otyughNumbers.add(numOtyughs);
    otyughNumbers.add(numOtyughsOkay);
    menu.add(otyughNumbers);

    menubar.add(menu);
    menubar.add(startQuest);
    menubar.add(restartQuest);
    menubar.add(quit);
    this.setJMenuBar(menubar);

    this.dungeon = dungeon;
  }

  @Override
  public void setFeatures(Features f) {
    startQuest.addActionListener(l -> f.startGame());
    restartQuest.addActionListener(l -> f.restartGame());
    rowsOkay.addActionListener(l -> f.processRows());
    columnsOkay.addActionListener(l -> f.processColumns());
    interconnectivityOkay.addActionListener(l -> f.processInterconnectivity());
    isWrapOkay.addActionListener(l -> f.processIsWrap());
    treasurePercentOkay.addActionListener(l -> f.processTreasurePercent());
    numOtyughsOkay.addActionListener(l -> f.processNumOtyughs());
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public String getRows() {
    return rows.getText();
  }

  @Override
  public String getColumns() {
    return columns.getText();
  }

  @Override
  public String getInterconnectivity() {
    return interconnectivity.getText();
  }

  @Override
  public String getTreasurePerecentage() {
    return treasureArrowPercent.getText();
  }

  @Override
  public String getNumOtyughs() {
    return numOtyughs.getText();
  }

  @Override
  public String getIsWrap() {
    return isWrap.getText();
  }

  @Override
  public void clearRows() {
    rows.setText("");
  }

  @Override
  public void resetRowsFocus() {
    rows.setFocusable(true);
  }

  @Override
  public void clearColumns() {
    columns.setText("");
  }

  @Override
  public void resetColumnsFocus() {
    columns.setFocusable(true);
  }

  @Override
  public void clearInterconnectivity() {
    interconnectivity.setText("");
  }

  @Override
  public void resetInterconnectivityFocus() {
    interconnectivity.setFocusable(true);
  }

  @Override
  public void clearTreasurePercent() {
    treasureArrowPercent.setText("");
  }

  @Override
  public void resetTreasureFocus() {
    treasureArrowPercent.setFocusable(true);
  }

  @Override
  public void clearNumOtyughs() {
    numOtyughs.setText("");
  }

  @Override
  public void resetNumOtyughsFocus() {
    numOtyughs.setFocusable(true);
  }

  @Override
  public void clearIsWrap() {
    isWrap.setText("");
  }

  @Override
  public void resetIsWrapFocus() {
    isWrap.setFocusable(true);
  }

  @Override
  public void addPanel() {
    panel = new DungeonPanel(dungeon);

    panel.setLayout(new GridLayout());

    // https://www.javatpoint.com/java-jscrollpane
    JScrollPane scrollableDungeon = new JScrollPane(panel);
    this.getContentPane().add(scrollableDungeon);

    this.pack();
    setLocationRelativeTo(null);
    JLabel tempLabel;

    for (int i = 0; i < dungeon.getDimensionRow(); i++) {
      for (int j = 0; j < dungeon.getDimensionColumn(); j++) {

        try {
          if (dungeon.getLocationAt(i, j).getIsTraversed()) {
            List<String> dirInitials = dungeon.getLocationAt(i, j).getHasConnectionAt().stream()
                    .map(dir -> dir.name().substring(0, 1)).collect(Collectors.toList());
            StringBuilder imageName = new StringBuilder();
            if(dirInitials.contains("N")) {
              imageName.append("N");
            }

            if(dirInitials.contains("S")) {
              imageName.append("S");
            }

            if(dirInitials.contains("E")) {
              imageName.append("E");
            }

            if(dirInitials.contains("W")) {
              imageName.append("W");
            }

            tempLabel = new JLabel(new ImageIcon(ImageIO.read(
                    new File("./dungeon-images/dungeon-images/color-cells/"
                            + imageName +".png"))));

          } else {
            tempLabel = new JLabel(new ImageIcon(ImageIO.read(new File("./dungeon-images/dungeon-images/blank.png"))));
          }
          panel.add(tempLabel);
        } catch (IOException ioe) {
          System.out.println();

          ioe.printStackTrace();
        }
      }
    }
  }
}
