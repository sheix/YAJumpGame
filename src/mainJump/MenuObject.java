package mainJump;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MenuObject extends gameObject {

    private ArrayList<String> items;
    public int SelectedIndex;
    public boolean isSelected;

    public MenuObject(ArrayList<String> _items)
    {
        SelectedIndex = 0;
        isSelected = false;
        x = 10; y= 10; dim = new Dimension(10,10);
        items = _items;
    }
    @Override
    public void Draw(Graphics2D g2) {
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            isSelected = true;
        }
        if (key == KeyEvent.VK_UP) {
            increaseSelectedIndex();
        }

        if (key == KeyEvent.VK_DOWN) {
            decreaseSelectedIndex();
        }

    }

    @Override
    public boolean IsInWindow(double w, double h)
    {
        return dataModel.INSTANCE.isGameOver;
    }

    private void increaseSelectedIndex()
    {
        SelectedIndex++;
        if (SelectedIndex >= items.size())
            SelectedIndex = 0;
    }

    private void decreaseSelectedIndex()
    {
        SelectedIndex--;
        if (SelectedIndex < 0)
            SelectedIndex = items.size()-1;
    }
}
