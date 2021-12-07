package dungeon.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import dungeon.controller.DungeonViewController;
import dungeon.controller.Features;

public interface DungeonView {

  void setFeatures(Features f);

  void makeVisible();

  void refresh();

  String getRows();

  String getColumns();

  String getInterconnectivity();

  String getTreasurePerecentage();

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
}
