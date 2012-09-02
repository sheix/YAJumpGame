package mainJump;

import java.awt.*;
import java.awt.font.FontRenderContext;

public class ScoreObject extends gameObject {


	public ScoreObject() {
		gravityObject = false;

	}
	
	
	@Override
	public void Draw(Graphics2D g2) {
        String ca = String.format("%d", dataModel.INSTANCE.score);

        g2.drawString(ca, 25, 25);
	}

}
