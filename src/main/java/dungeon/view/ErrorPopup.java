package dungeon.view;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class ErrorPopup extends JFrame {

  public ErrorPopup(DungeonView view, String message) {

    this.setSize(300, 200);
    this.setLocation(250, 250);
    this.setLayout(new FlowLayout());
    this.setBackground(new Color(0, 0, 0));
    this.setFont(new Font("TimesRoman", Font.BOLD, 14));
    JPanel panel = new JPanel();
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


    JLabel errorMessage = new JLabel();
    errorMessage.setForeground(Color.RED);
    errorMessage.setText(message);
    errorMessage.setSize(200, 200);
    errorMessage.setFont(new Font("TimesRoman", Font.BOLD, 20 ));

    panel.add(errorMessage);
    this.add(panel);

    this.setVisible(true);
    this.setFocusable(true);
    this.requestFocus();

    this.addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
        view.setFocus(true);
      }
    });
  }

}
