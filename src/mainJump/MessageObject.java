package mainJump;

import java.awt.*;

public class MessageObject extends gameObject {

    private String message;

    public MessageObject(String _message)
    {
        message = _message;
        gravityObject = false;
        x = 1;
        y = dataModel.INSTANCE.getHeightPercent(40);
        dim = new Dimension(10,10);
    }
    @Override
    public void Draw(Graphics2D g2) {
        g2.drawString(message, 25, y);
    }

    @Override
    public void OnTimer()
    {
        y += 2;
    }
}
