package dungeon.view;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;
import javax.swing.*;

import dungeon.controller.DungeonViewController;
import dungeon.controller.Features;
import dungeon.model.Location;
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

  private DungeonPanel dungeonPanel;
  private LocationPanel locationPanel;
  private JTextField rows;
  private JTextField columns;
  private JTextField interconnectivity;
  private JTextField isWrap;
  private JTextField numOtyughs;
  private JTextField treasureArrowPercent;
  private ReadOnlyDungeon dungeon;

  int shootDistance;

  public DungeonSwingView(ReadOnlyDungeon dungeon) {

    setSize(650, 550);
    setLocation(200, 200);
    getContentPane().setBackground(new Color(0, 0, 0));
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
    dungeonPanel = new DungeonPanel(dungeon);
    locationPanel = new LocationPanel(dungeon);
    locationPanel.setBackground(new Color(0, 0, 0));

    JScrollPane scrollableDungeon = new JScrollPane(dungeonPanel);
    scrollableDungeon.setBackground(new Color(0, 0, 0));
    this.add(scrollableDungeon, BorderLayout.CENTER);
    this.add(locationPanel, BorderLayout.SOUTH);

    JLabel caveEntities = new JLabel();
    caveEntities.setSize(500, 300);
    caveEntities.setName("baseImage");
    locationPanel.putClientProperty(caveEntities.getName(), caveEntities);
    locationPanel.add(caveEntities);


    // https://www.javatpoint.com/java-jscrollpane

  }

  @Override
  public void setFeatures(Features f, KeyListener key) {
    startQuest.addActionListener(l -> f.startGame());
    restartQuest.addActionListener(l -> f.restartGame());
    quit.addActionListener(l -> System.exit(0));
    rowsOkay.addActionListener(l -> f.processRows());
    columnsOkay.addActionListener(l -> f.processColumns());
    interconnectivityOkay.addActionListener(l -> f.processInterconnectivity());
    isWrapOkay.addActionListener(l -> f.processIsWrap());
    treasurePercentOkay.addActionListener(l -> f.processTreasurePercent());
    numOtyughsOkay.addActionListener(l -> f.processNumOtyughs());
    this.addKeyListener(key);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.repaint();
    this.revalidate();
    this.resetFocus();
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
  public String getTreasurePercentage() {
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

    dungeonPanel.setLayout(new GridBagLayout());
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
            String imageName = dungeonPanel.getImageNameOfCell(i, j);

            tempLabel = new JLabel(new ImageIcon(ImageIO.read(
                    new File("./dungeon-images/dungeon-images/color-cells/"
                            + imageName + ".png"))));
          } else {
            tempLabel = new JLabel(new ImageIcon(ImageIO.read(
                    new File("./dungeon-images/dungeon-images/blank.png"))));
          }

          MouseListener ml = new MyMouseAdapter(controller);
          tempLabel.addMouseListener(ml);
          tempLabel.setName(i + " " + j);
          tempLabel.setSize(new Dimension(250,250));
          dungeonPanel.add(tempLabel, gbc);
          dungeonPanel.putClientProperty(i + " " + j, tempLabel);
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }
      }
    }
  }

  @Override
  public void clearPanel() {
    dungeonPanel.removeAll();
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void shootOtyugh() {

  }

  @Override
  public void addPopup() {
    PopupPanel popup = new PopupPanel(this);
  }

  @Override
  public DungeonPanel getDungeonPanel() {
    return dungeonPanel;
  }

  @Override
  public void setDistance(String distance) {
    try {
      shootDistance = Integer.parseInt(distance);
    } catch (NoSuchElementException | IllegalArgumentException iae) {
      // do nothing
    }
  }

  @Override
  public void pick() {

  }

  @Override
  public void errorPopup(String message) {
    ErrorPopup errorPopup = new ErrorPopup(message);
  }


}
