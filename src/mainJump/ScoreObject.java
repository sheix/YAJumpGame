package mainJump;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.TextArea;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class ScoreObject extends gameObject {

	
	int score;
	
	public ScoreObject() {
		gravityObject = false;
		score = 0;
	}
	
	
	@Override
	public void Draw(Graphics2D g2) {

		FontRenderContext frc = g2.getFontRenderContext();
        char[] ca = String.format("%d", score).toCharArray();
		
        g2.drawChars(ca, 0, ca.length, 2, 1);
               
	}
	
	public void increment(int x)
	{
		score += x;
	}

}
