package dungeon.view;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

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

  private JButton okay;

  private DungeonPanel dungeonPanel;
  private LocationPanel locationPanel;
  private JPanel panel;
  private JPanel playerInfoPanel;
  private JTextField rows;
  private JTextField columns;
  private JTextField interconnectivity;
  private JTextField isWrap;
  private JTextField numOtyughs;
  private JTextField treasureArrowPercent;
  private JLabel playerAction;
  private ReadOnlyDungeon dungeon;

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
    okay = new JButton("Okay");

    JMenu rowsMenu = new JMenu("Rows");
    rows = new JTextField();
    rows.setColumns(5);
    rowsMenu.add(rows);
    menu.add(rowsMenu);

    JMenu columnsMenu = new JMenu("Columns:");
    columns = new JTextField();
    columns.setColumns(5);
    columnsMenu.add(columns);
    menu.add(columnsMenu);

    JMenu interconnectivityMenu = new JMenu("Interconnectivity:");
    interconnectivity = new JTextField();
    interconnectivity.setColumns(5);
    interconnectivityMenu.add(interconnectivity);
    menu.add(interconnectivityMenu);

    JMenu isWrapMenu = new JMenu("Wrapping:");
    isWrap = new JTextField();
    isWrap.setColumns(5);
    isWrapMenu.add(isWrap);
    menu.add(isWrapMenu);

    JMenu treasureArrowPercentMenu = new JMenu("Treasure Percent:");
    treasureArrowPercent = new JTextField();
    treasureArrowPercent.setColumns(5);
    treasureArrowPercentMenu.add(treasureArrowPercent);
    menu.add(treasureArrowPercentMenu);

    JMenu otyughNumbers = new JMenu("Otyugh(s):");
    numOtyughs = new JTextField();
    numOtyughs.setColumns(5);
    otyughNumbers.add(numOtyughs);
    menu.add(otyughNumbers);
    menu.add(okay);

    menubar.add(menu);
    menubar.add(startQuest);
    menubar.add(restartQuest);
    menubar.add(quit);
    this.setJMenuBar(menubar);

    this.dungeon = dungeon;
    dungeonPanel = new DungeonPanel(dungeon);
    locationPanel = new LocationPanel(dungeon);

    playerAction = new JLabel();
    playerAction.setSize(600, 350);
    playerAction.setForeground(Color.RED);
    playerAction.setFont(new Font("TimesRoman", Font.BOLD, 20));
    this.add(playerAction, BorderLayout.NORTH);

    playerInfoPanel = new PlayerInfoPanel(dungeon);
    addPlayerInfoPanel();
    this.add(playerInfoPanel, BorderLayout.EAST);


    // https://www.javatpoint.com/java-jscrollpane
    JScrollPane scrollableDungeon = new JScrollPane(dungeonPanel);
    scrollableDungeon.setBackground(new Color(0, 0, 0));
    this.add(scrollableDungeon, BorderLayout.CENTER);

    JLabel caveEntities = new JLabel();
    caveEntities.setSize(500, 300);
    caveEntities.setName("baseImage");
    locationPanel.putClientProperty(caveEntities.getName(), caveEntities);
    locationPanel.add(caveEntities);
    locationPanel.setBackground(new Color(0, 0, 0));
    this.add(locationPanel, BorderLayout.SOUTH);

  }

  @Override
  public void addPlayerInfoPanel() {
    playerInfoPanel.setLayout(new GridBagLayout());
    playerInfoPanel.setBackground(new Color(255, 255, 255));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(1, 1, 1, 1);
    gbc.weightx = 0;
    gbc.weighty = 0;
    gbc.fill = GridBagConstraints.NONE;

    JLabel tempLabel;

    for (int i = 0; i < 4; i++) {
      gbc.gridx = i;
      for (int j = 0; j < 2; j++) {
        gbc.gridy = j;
        tempLabel = new JLabel();
        tempLabel.setName(i+""+j);
        System.out.println(tempLabel.getName());
        playerInfoPanel.putClientProperty(tempLabel.getName(), tempLabel);
        playerInfoPanel.add(tempLabel, gbc);
      }
    }

    try {
      JLabel arrowl = ((JLabel) playerInfoPanel.getClientProperty("00"));
      arrowl.setIcon(new ImageIcon(ImageIO.read(
              new File("./dungeon-images/dungeon-images/arrow-white.png"))));

      JLabel rubyl = ((JLabel) playerInfoPanel.getClientProperty("10"));
      rubyl.setIcon(new ImageIcon(ImageIO.read(
              new File("./dungeon-images/dungeon-images/ruby.png"))));

      JLabel diamondl = ((JLabel) playerInfoPanel.getClientProperty("20"));
      diamondl.setIcon(new ImageIcon(ImageIO.read(
              new File("./dungeon-images/dungeon-images/diamond.png"))));

      JLabel sapphirel = ((JLabel) playerInfoPanel.getClientProperty("30"));
      sapphirel.setIcon(new ImageIcon(ImageIO.read(
              new File("./dungeon-images/dungeon-images/sapphire.png"))));
    } catch (IOException ex) {
      // do nothing
      ex.printStackTrace();
    }

    playerInfoPanel.repaint();

  }

  @Override
  public void setFeatures(Features f, KeyListener key) {
    startQuest.addActionListener(l -> f.startGame());
    restartQuest.addActionListener(l -> f.restartGame());
    quit.addActionListener(l -> System.exit(0));
    okay.addActionListener(l -> f.processGameSettings());
    this.addKeyListener(key);
  }

  @Override
  public void makeVisible(boolean visible) {
    this.setVisible(visible);
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

    if(dungeon.gameBegin()) {
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
            tempLabel.setSize(new Dimension(250, 250));
            dungeonPanel.add(tempLabel, gbc);
            dungeonPanel.putClientProperty(i + " " + j, tempLabel);
          } catch (IOException ioe) {
            ioe.printStackTrace();
          }
        }
      }
    }
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void errorPopup(String message) {
    ErrorPopup errorPopup = new ErrorPopup(this, message);
    errorPopup.setFocusable(true);
    errorPopup.requestFocus();
  }

  @Override
  public void setFocus(boolean b) {
    if(b) {
      resetFocus();
    } else {
      setFocusable(false);
    }
  }

  @Override
  public void removeDungeonPanelListeners() {
    for(int i=0; i< dungeon.getDimensionRow(); i++) {
      for(int j = 0; j < dungeon.getDimensionColumn(); j++) {
        JLabel tempLabel = ((JLabel) dungeonPanel.getClientProperty(i + " " + j));
        MouseListener[] mls = tempLabel.getMouseListeners();
        tempLabel.removeMouseListener(mls[0]);
      }
    }
  }

  @Override
  public void clearDungeonPanel() {
    if(dungeon.gameBegin()) {
      dungeonPanel.emptyTheLocations();
    }
  }

  @Override
  public void setPlayerAction(String message) {
    playerAction.setText(message);
  }

  @Override
  public void removeDungeonPanel() {
    dungeonPanel.removeAll();
  }

  @Override
  public void addMouseListeners(DungeonViewController controller) {
    dungeonPanel.addMouseListeners(controller);
  }
}
