package mainJump;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class VisibleArea extends JPanel implements ActionListener {

	Timer timer;
	private ArrayList<gameObject> objects;
	gameObject player;
	int score;
    private final PlatformBuilder platformBuilder = new PlatformBuilder(this);


    public VisibleArea()
	{
		score = 1;
		addKeyListener(new TAdapter());
		
		setFocusable(true);
		
		setDoubleBuffered(true);
		player = new Player();
        ScoreObject scoreObject = new ScoreObject();

        objects = new ArrayList<gameObject>();
        objects.add(player);
        objects.add(scoreObject);

        timer = new Timer(25, this);
        timer.setInitialDelay(300);
        timer.start();
    }

    public int getScore() {
        return score;
    }

    public ArrayList<gameObject> getObjects() {
        return objects;
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

      DrawAllShapes(g2);
      
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
        if (score == 2)
            platformBuilder.MakeInitialPlatforms();
        Random r = new Random();
        platformBuilder.TryMakePlatform(r);
    }

    private void KillWrongObjects(double w, double h) {
		ArrayList<gameObject> toDeleteObjects = new ArrayList<gameObject>();
		for (gameObject o : objects) {
            if (!(o instanceof ScoreObject))
			    if (!o.IsInWindow(w,h)  )
				toDeleteObjects.add(o);
		}
		
		objects.removeAll(toDeleteObjects);

        if (toDeleteObjects.contains(player))
        {
            timer.stop();
            // game Over
        }
	}


	private void DrawAllShapes(Graphics2D g2) {
    	for (gameObject o : objects) {
			o.Draw(g2);
		}
		
	}
	
	private void SetPlayerOnPlatform()
	{
		for (gameObject o : objects)
			if (o instanceof Platform)
			{
				if (IsOnSameLevelWithPlayer(o) && IfInsideBoundariesOfThePlatform(o))
				{
					((Player)player).onPlatform = true;
					return;
				}
			}
		((Player)player).onPlatform = false;
	}

    private boolean IfInsideBoundariesOfThePlatform(gameObject o) {
        int margin = player.dim.width / 2 - 1;
        return ((o.x<player.x-margin)&&(o.x + o.dim.width + margin >player.x + player.dim.width));
    }

    private boolean IsOnSameLevelWithPlayer(gameObject o) {
        return (o.y == player.y + player.dim.height);
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
