package dungeon.view;

import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import dungeon.controller.DungeonViewController;
import dungeon.controller.Features;

public interface DungeonView {

  void setFeatures(Features f, KeyListener keys);

  void makeVisible();

  void refresh();

  String getRows();

  String getColumns();

  String getInterconnectivity();

  String getTreasurePercentage();

  String getNumOtyughs();

  String getIsWrap();

  void clearRows();

  void resetRowsFocus();

  void clearColumns();

  void resetColumnsFocus();

  void clearInterconnectivity();

  void resetInterconnectivityFocus();

  void clearTreasurePercent();

  void resetTreasureFocus();

  void clearNumOtyughs();

  void resetNumOtyughsFocus();

  void clearIsWrap();

  void resetIsWrapFocus();

  void addPanel(DungeonViewController controller);

  void clearPanel();

  void resetFocus();

  void shootOtyugh();

  void addPopup();

  void addKeyListener(KeyListener listener);

  DungeonPanel getDungeonPanel();

  void setDistance(String text);

  void pick();

  void errorPopup(String message);
}
