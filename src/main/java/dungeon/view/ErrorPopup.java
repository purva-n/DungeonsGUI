package dungeon.view;

import java.awt.*;

import javax.swing.*;

public class ErrorPopup {

  public ErrorPopup(String message) {

    JFrame frame = new JFrame();
    frame.setSize(300, 200);
    frame.setLocation(250, 250);
    frame.setLayout(new FlowLayout());
    JPanel panel = new JPanel();

    JLabel errorMessage = new JLabel();
    errorMessage.setText(message);
    errorMessage.setSize(200, 200);
    errorMessage.setFont(new Font("TimesRoman", Font.BOLD, 12 ));

    panel.add(errorMessage);
    frame.add(panel);

    frame.setVisible(true);
    frame.setFocusable(true);
    frame.requestFocus();
  }

}
