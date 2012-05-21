package mainJump;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class Platform extends gameObject {

	
	int width;
	
	Platform(int x1,int x2, int height)
	{
		x = x1;
		width = x2-x1;
		y = height;
		gravityObject = true;
		dim = new Dimension(width, 5); 
		
	}
	
	@Override
	public void Draw(Graphics2D g2) {
		s = (Shape)(new Rectangle2D.Double(x,y,dim.getWidth(),dim.getHeight()));
		g2.draw(s);
	}
	
	@Override
	public void OnTimer() {
		super.OnTimer();
	}

}
