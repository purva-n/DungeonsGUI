package dungeon.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PopupPanel {

  JFrame frame = new JFrame();
  JLabel label = new JLabel("Distance");
  JTextField distance = new JTextField();
  JPanel panel = new JPanel();
  JButton ok = new JButton("Show");


  public PopupPanel() {
    ok.setActionCommand("Okay");

    frame.setLayout(new FlowLayout());
    panel.add(label);
    panel.add(distance);
    panel.add(ok);
    frame.add(panel);

    frame.setSize(400, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    ok.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        panel.setSize(distance.getWidth(), distance.getHeight());
        panel.setLocation(distance.getLocation().x, distance.getLocation().y
                + distance.getHeight());
        panel.setVisible(true);
      }
    });
  }

  public String getDistance() {
    return distance.getText();
  }
}
