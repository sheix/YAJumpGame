package mainJump;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;

public class Player extends gameObject {

	int jumping;
	
	public Player()
	{
		x = 100;
		y = 100;
		
		jumping = 0;
		
		dim = new Dimension(10, 10);
	}
	
	@Override
	public void Draw(Graphics2D g2) {
		
		s = (Shape)(new Ellipse2D.Double(x,y,dim.getWidth(),dim.getHeight()));
		
		g2.draw(s);
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_UP) {
            StartJump();
        }
		
		if (key == KeyEvent.VK_LEFT) {
            x--;
        }
		
		if (key == KeyEvent.VK_RIGHT)
		{
			x++;
		}
	}

	@Override
	public void OnTimer() {
		y++;
	};
	
	private void StartJump() {
		
		if (jumping == 0)
		{
			y = y - 10;
			jumping = 3;
			return;
		}
		
		if (jumping == 1)
		{
			y = y - 5;
			jumping--;
			return;
		}
		
		if (jumping == 2)
		{
			y = y - 1 ;
			jumping--;
			return;
		}
		
		if (jumping == 3)
		{
			y = y + 1;
			jumping--;
			return;
		}
	
		
	}


	
	
}
