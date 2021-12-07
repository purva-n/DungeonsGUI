package dungeon.view;

import java.awt.*;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.*;

import dungeon.controller.DungeonViewController;
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
  //private JScrollPane scrollableDungeon;
  private ReadOnlyDungeon dungeon;

  public DungeonSwingView(ReadOnlyDungeon dungeon) {

    setSize(450, 550);
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
    //this.scrollableDungeon = new JScrollPane(panel);
  }

  @Override
  public void setFeatures(Features f) {
    startQuest.addActionListener(l -> f.startGame());
    restartQuest.addActionListener(l -> f.restartGame());
    quit.addActionListener(l -> System.exit(0));
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
    panel.revalidate();
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
  public void addPanel(DungeonViewController controller) {
    if (panel == null) {
      panel = new DungeonPanel(dungeon);
    } else {
      //scrollableDungeon.remove(panel);
      clearPanel();
    }
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(1, 1, 1, 1);
    gbc.weightx = 0;
    gbc.weighty = 0;
    gbc.fill = GridBagConstraints.NONE;

    JLabel tempLabel;

    for (int i = 0; i < dungeon.getDimensionRow(); i++) {
      gbc.gridy = i;
      for (int j = 0; j < dungeon.getDimensionColumn(); j++) {
        gbc.gridx = j;
        try {
          if (dungeon.getLocationAt(i, j).getIsTraversed()) {
            String imageName = getImageNameOfCell(i, j);

            tempLabel = new JLabel(new ImageIcon(ImageIO.read(
                    new File("./dungeon-images/dungeon-images/color-cells/"
                            + imageName + ".png"))));
          } else {
            tempLabel = new JLabel(new ImageIcon(ImageIO.read(
                    new File("./dungeon-images/dungeon-images/blank.png"))));
          }

          MouseListener ml = new MyMouseAdapter(controller, this);
          tempLabel.addMouseListener(ml);
          tempLabel.setName(i + " " + j);
          panel.add(tempLabel, gbc);
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }
      }
    }

    // https://www.javatpoint.com/java-jscrollpane
//    JScrollPane scrollableDungeon = new JScrollPane(panel);
//    this.add(scrollableDungeon);

    this.add(panel);
  }

  @Override
  public void clearPanel() {
    panel.removeAll();
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
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
}
