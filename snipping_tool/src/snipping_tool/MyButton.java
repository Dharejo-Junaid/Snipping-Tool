package snipping_tool;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import java.awt.event.ActionListener;

class MyButton extends JButton {

    MyButton(String text, ImageIcon icon, ActionListener l) {
        super(text);
        this.setIcon(icon);
        this.addActionListener(l);
        this.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        this.setVerticalTextPosition(JButton.BOTTOM);
        this.setHorizontalTextPosition(JButton.CENTER);
        this.setMargin(new Insets(10, 20, 10, 20));
        this.setPreferredSize(new Dimension(110, 90));
        this.setFocusable(false);
        this.setIconTextGap(10);
    }
}
