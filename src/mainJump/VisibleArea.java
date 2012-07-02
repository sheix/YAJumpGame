package mainJump;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class VisibleArea extends JPanel implements ActionListener {

	Timer timer;
	private ArrayList<gameObject> objects;
	gameObject player;
	int score;
	
	
	
	public VisibleArea()
	{
		score = 1;
		addKeyListener(new TAdapter());
		
		setFocusable(true);
		
		setDoubleBuffered(true);
		player = new Player();
		
		
		timer = new Timer(25, this);
        timer.start();
        
        
        
        objects = new ArrayList<gameObject>();
        objects.add(player);
        
        for(int i = 0; i<10;i++)
        {
        	objects.add(new Platform(10,100,(i+1) * 10 ));
        }
        
	}
	
	private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
        	player.keyPressed(e);
        }
    }
	
	public void paint(Graphics g)
    {
      super.paint(g);

      Graphics2D g2 = (Graphics2D) g;

      RenderingHints rh =
            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

      rh.put(RenderingHints.KEY_RENDERING,
             RenderingHints.VALUE_RENDER_QUALITY);

      g2.setRenderingHints(rh);

      Dimension size = getSize();
      double w = size.getWidth();
      double h = size.getHeight();
      
      
      DrawAllShapes(w,h, g2);
      
      KillWrongObjects(w,h);
      
      Toolkit.getDefaultToolkit().sync();
      g.dispose();
      
      
        }
	
	
    private void MoveAllShapes() {
    	for (gameObject o : objects) {
			o.OnTimer();
    	}
		
	}


	private void BornNewObjects() {
		Random r = new Random();
		if (r.nextInt(10*(1+(int)(1/Math.log(score))))==0)
		{
			int width = (int)((1/Math.log(score))*(getSize().width))/3;
			int x = r.nextInt(getSize().width - width);
			Platform p = new Platform(x, x+width, 10);
			objects.add(p);
		}
	}


	private void KillWrongObjects(double w, double h) {
		ArrayList<gameObject> toDeleteObjects = new ArrayList<gameObject>();
		for (gameObject o : objects) {
			if (!o.IsInWindow(w,h))
				toDeleteObjects.add(o);
		}
		
		objects.removeAll(toDeleteObjects);
	}


	private void DrawAllShapes(double w, double h, Graphics2D g2) {
    	for (gameObject o : objects) {
			o.Draw(g2);
		}
		
	}
	
	private void SetPlayerOnPlatform()
	{
		for (gameObject o : objects)
			if (o instanceof Platform)
			{
				if ((o.y == player.y + player.dim.height) && ((o.x<player.x)&&(o.x + o.dim.width>player.x + player.dim.width)))
				{
					((Player)player).onPlatform = true;
					return;
				}
			}
		((Player)player).onPlatform = false;
			return;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		score++;
		BornNewObjects();
	    MoveAllShapes();
	    SetPlayerOnPlatform();
		repaint();
	}
	
	
	
}
