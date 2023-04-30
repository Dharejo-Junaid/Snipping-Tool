package snipping_tool;

import javax.swing.JFrame;
import javax.swing.ImageIcon;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.AWTException;
import java.awt.Robot;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class ScreenCapture extends JFrame implements MouseListener, MouseMotionListener {

    private Point startPt;
    private Point endPt;

    private Rectangle imageRect = null;
    private BufferedImage buff = null;

    private boolean flag = false;

    private int X = 0, Y = 0, WIDTH = 0, HEIGHT = 0;
    private Color selectionColor;

    public ScreenCapture() {
        this(Color.RED);
    }

    public ScreenCapture(Color selectionColor) {

        this.selectionColor = selectionColor;


        startPt = new Point();
        endPt = new Point();

        // setting full frame size;
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Mouse Listener and Mouse Motion listener;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        // Blurring the frame;
        this.setUndecorated(true);
        this.setOpacity(0.2f);
    }

    public void captureImage() {

        flag = false;
        this.setVisible(true);

        while (! flag) {
            try {
                Thread.sleep(250);
            }

            catch (InterruptedException e) {}
        }
    }

    // coloring selected region;
    @Override
    public void paint(Graphics g) {

        super.paint(g);

        setValues();

        g.setColor(selectionColor);
        g.fillRect(X, Y, WIDTH, HEIGHT);
    }

    // calculating start point and width & height;
    private void setValues() {
        X = Math.min(startPt.x, endPt.x);
        Y = Math.min(startPt.y, endPt.y);
        WIDTH = Math.abs(startPt.x - endPt.x);
        HEIGHT = Math.abs(startPt.y - endPt.y);
    }

    public void setSelectionColor(Color color) {
        this.selectionColor = color;
    }


    // starting point of selected region;
    @Override
    public void mousePressed(MouseEvent e) {

        startPt.x = e.getXOnScreen();
        startPt.y = e.getYOnScreen();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        // if no any region is selected;
        if(WIDTH == 0 || HEIGHT == 0) {
            flag = true;
            this.dispose();
            return;
        }

        // hiding frame to capture clear image;
        this.setVisible(false);

        // imageRect is selected region;
        imageRect = new Rectangle(X, Y, WIDTH, HEIGHT);
        try {
            // capturing image of selected region;
            buff = new Robot().createScreenCapture(imageRect);
        }

        catch (AWTException ex) {}

        flag = true;
        this.dispose();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        endPt.x = e.getXOnScreen();
        endPt.y = e.getYOnScreen();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    public boolean isImageCaptured() {
        return (buff != null);
    }

    public BufferedImage getImage() {
        return buff;
    }

    public ImageIcon getImageIcon() {
        return new ImageIcon(buff);
    }
}
