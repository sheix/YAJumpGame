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
	
	
	
	
	public VisibleArea()
	{
		
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
		if (r.nextInt(25)==0)
		{
			Platform p = new Platform(r.nextInt(5)+10, r.nextInt(5)+20, 10);
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	    BornNewObjects();
	    MoveAllShapes();
		repaint();
	}
	
	
	
}
