package mainJump;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;

public abstract class gameObject {
	
	
	Dimension dim;
	int x; 
	int y;
	Shape s;
	boolean gravityObject;
	
	abstract public void Draw(Graphics2D g2);


	public boolean IsInWindow(double w, double h) {
        return (x > 0) && (x + dim.width < w) && (y > 0) && (y + dim.height < h);
    }


	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	public void OnTimer() {
		if (gravityObject)
			y++;
		
	}



	
}
