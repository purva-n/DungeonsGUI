package dungeon.view;

import java.awt.*;
import java.util.Map;

import javax.swing.*;

import dungeon.model.ReadOnlyDungeon;
import dungeon.model.TreasureType;

public class PlayerInfoPanel extends JPanel {

  private final ReadOnlyDungeon dungeon;

  public PlayerInfoPanel(ReadOnlyDungeon dungeon) {
    this.dungeon = dungeon;
    setSize(600, 600);
  }

  @Override
  public void paintComponent(Graphics g) {
    if (dungeon.gameBegin()) {
      Map<TreasureType, Integer> treasures = dungeon.getPlayersCollectedTreasure();

      JLabel arrowCount = ((JLabel) getClientProperty("01"));
      arrowCount.setFont(new Font("TimesRoman", Font.PLAIN, 24));
      arrowCount.setForeground(Color.WHITE);
      arrowCount.setText(String.valueOf(dungeon.getPlayerArrowCount()));

      JLabel rubyCount = ((JLabel) getClientProperty("11"));
      if (treasures.containsKey(TreasureType.RUBY)) {
        rubyCount.setFont(new Font("TimesRoman", Font.PLAIN, 24));
        rubyCount.setForeground(Color.WHITE);
        rubyCount.setText(String.valueOf(treasures.get(TreasureType.RUBY)));
      } else {
        rubyCount.setText("0");
      }

      JLabel diamondCount = ((JLabel) getClientProperty("21"));
      if (treasures.containsKey(TreasureType.DIAMOND)) {
        diamondCount.setFont(new Font("TimesRoman", Font.PLAIN, 24));
        diamondCount.setForeground(Color.WHITE);
        diamondCount.setText(String.valueOf(treasures.get(TreasureType.DIAMOND)));
      } else {
        diamondCount.setText("0");
      }

      JLabel sapphireCount = ((JLabel) getClientProperty("31"));
      if (treasures.containsKey(TreasureType.SAPPHIRE)) {
        sapphireCount.setFont(new Font("TimesRoman", Font.PLAIN, 24));
        sapphireCount.setForeground(Color.WHITE);
        sapphireCount.setText(String.valueOf(treasures.get(TreasureType.SAPPHIRE)));
      } else {
        sapphireCount.setText("0");
      }

    }
  }
}
