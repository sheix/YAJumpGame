package mainJump;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;

public class Player extends gameObject {

    int jumping;
    int dx = 0;
    int signx;
    public boolean onPlatform;

    public Player() {
        x = dataModel.INSTANCE.getWidthPercent(50);
        y = dataModel.INSTANCE.getHeightPercent(30);
        gravityObject = true;
        jumping = 0;
        dim = new Dimension(10, 10);
    }

    @Override
    public void Draw(Graphics2D g2) {

        s = new Ellipse2D.Double(x, y, dim.getWidth(), dim.getHeight());

        g2.draw(s);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            jumping = 0;
        }

        if (key == KeyEvent.VK_LEFT) {
            signx = -1;
            dx = 1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            signx = 1;
            dx = 1;
        }

    }


    public void OnTimer() {
        countJump();
        countDx();
        super.OnTimer();
    }

    private void countDx() {
        if (dx == 0) return;
        countSignDx();

    }

    private void countSignDx() {
        if (dx == 1) {
            x = (x + signx*5);
            dx++;
            return;
        }
        if (dx == 2) {
            x = x + signx*3;
            dx++;
            return;
        }
        if (dx == 3) {
            x = x + signx;
            dx = 0;
        }
    }

    private void countJump() {

        if (onPlatform) {
            y = y - 1;
        }


        if ((jumping == 0) && onPlatform) {
            y = y - 50;
            jumping = 1;
            dataModel.INSTANCE.score += 100;
            return;
        }

        if (jumping == 0) {
            jumping = 3;
            return;
        }

        if (jumping == 1) {
            y = y - 20;
            jumping++;
            return;
        }

        if (jumping == 2) {
            y = y - 10;
            jumping++;
            return;
        }

        if (jumping == 3) {
            y = y + 1;
        }


    }


}
