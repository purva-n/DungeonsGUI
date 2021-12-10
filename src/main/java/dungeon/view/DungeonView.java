package dungeon.view;

import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import dungeon.controller.DungeonViewController;
import dungeon.controller.Features;

public interface DungeonView {

  void addPlayerInfoPanel();

  void setFeatures(Features f, KeyListener keys);

  void makeVisible(boolean visible);

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

  void resetFocus();

  void errorPopup(String message);

  void setFocus(boolean b);

  void removeDungeonPanelListeners();

  void clearPanel();

  void setPlayerAction(String message);
}
