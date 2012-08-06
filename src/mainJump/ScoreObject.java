package mainJump;

import java.awt.*;
import java.awt.font.FontRenderContext;

public class ScoreObject extends gameObject {

	
	int score;
	
	public ScoreObject() {
		gravityObject = false;
		score = 0;
	}
	
	
	@Override
	public void Draw(Graphics2D g2) {

		FontRenderContext frc = g2.getFontRenderContext();
        String ca = String.format("%d", score);

        g2.drawString(ca, 25, 25);
	}
	
	public void increment(int x)
	{
		score += x;
	}

}
