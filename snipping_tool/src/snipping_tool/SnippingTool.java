package snipping_tool;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SnippingTool extends JFrame {

    private JPanel buttonPanel;
    private MyButton newSnip, close;

    private ImageIcon toolIcon, cancelIcon;
    private ScreenCapture screenCapture;

    private Color selectionColor;

    private Thread thread;

    public SnippingTool() {

        // icon used on buttons;
        toolIcon = new ImageIcon(getClass().getResource("images/tool.png"));
        cancelIcon = new ImageIcon(getClass().getResource("images/close.png"));

        selectionColor = Color.RED;
        screenCapture = new ScreenCapture(selectionColor);

        // Create thread to capture image in new thread;
        // and to stop current thread;
        thread = new Thread(() -> {
            screenCapture.captureImage();
        });

        // set frame properties;
        this.setTitle("Snipping Tool");
        this.setIconImage(toolIcon.getImage());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Panel to set buttons on it;
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        // snip capture button;
        newSnip = new MyButton("New Snip", toolIcon, this::newSnipAction);
        buttonPanel.add(newSnip);

        // close button;
        close = new MyButton("Close", cancelIcon, this::closeAction);
        buttonPanel.add(close);

        // add panel to north side;
        this.add(buttonPanel, BorderLayout.NORTH);

        // Instruction text;
        JLabel instruction = new JLabel("Drag the cursor around the area you want to capture");
        instruction.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        instruction.setPreferredSize(new Dimension(430, 50));
        instruction.setHorizontalAlignment(JLabel.CENTER);
        this.add(instruction, BorderLayout.SOUTH);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        // overriding close operation to stop thread and then to close;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thread = null;
                super.windowClosing(e);
            }
        });
    }

    public void newSnipAction(ActionEvent e) {

        // create thread and capture image throw it;
        thread = new Thread(() -> {
            screenCapture = new ScreenCapture(selectionColor);
            screenCapture.captureImage();
        });

        thread.start();
    }

    public void closeAction(ActionEvent e) {
        thread = null;
        this.dispose();
    }

    public void showAndWait() {
        // show;
        this.setVisible(true);

        // wait;
        holdThread();
    }

    public ScreenCapture getScreenCapture() {
        return screenCapture;
    }

    public void setSelectionColor(Color selectionColor) {
        this.selectionColor = selectionColor;
    }

    // hold current thread here;
    // until snipped_tool is open;
    private void holdThread() {
        while(thread != null) {
            try {
                Thread.sleep(250);
            }
            catch (InterruptedException e) {}
        }
    }
}