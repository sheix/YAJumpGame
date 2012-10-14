package mainJump;

import javax.swing.JFrame;
import java.awt.*;

public class MainLoop extends JFrame {


    public MainLoop() {
        dataModel.INSTANCE.setDimensions(new Dimension(300,280));
        add(new VisibleArea());
        setTitle("Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(dataModel.INSTANCE.getDimension());
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);

    }

    public static void main(String[] args) {
        MainLoop loop = new MainLoop();
    }


}
