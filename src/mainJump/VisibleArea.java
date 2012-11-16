package mainJump;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VisibleArea extends JPanel implements ActionListener {

	Timer timer;
	private ArrayList<gameObject> objects;
	gameObject player;
    gameObject menu;

    private final PlatformBuilder platformBuilder = new PlatformBuilder(this);
    private ArrayList<String> menuItems;

    public VisibleArea()
	{
        menuItems = new ArrayList<String>();
        menuItems.add("New Game");
        menuItems.add("Exit");
		addKeyListener(new TAdapter());
        this.addComponentListener(new Listener());
        setFocusable(true);
		setDoubleBuffered(true);
        menu = new MenuObject(menuItems);
        InitializeNewGame();
    }

    private void InitializeNewGame() {
        dataModel.INSTANCE.isGameOver = false;
        player = new Player();
        ScoreObject scoreObject = new ScoreObject();
        dataModel.INSTANCE.score = 1;
        dataModel.INSTANCE.time = 1;
        objects = new ArrayList<gameObject>();
        objects.add(player);
        objects.add(scoreObject);
        InitializeTimer();
    }

    private void InitializeTimer() {
        timer = new Timer(35, this);
        timer.setInitialDelay(300);
        timer.start();
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

      DrawGameObjects(g2);
      
      KillWrongObjects(w,h);
      
      Toolkit.getDefaultToolkit().sync();
      g.dispose();
    }
	
	
    private void ActForAllObjects() {
    	for (gameObject o : objects) {
			o.OnTimer();
    	}
    }


	private void BornNewObjects() {
        if (dataModel.INSTANCE.time == 2)
            platformBuilder.MakeInitialPlatforms();
        platformBuilder.TryMakePlatform();
    }

    private void KillWrongObjects(double w, double h) {
		ArrayList<gameObject> toDeleteObjects = new ArrayList<gameObject>();
		for (gameObject o : objects) {
            if (!((o instanceof ScoreObject) || (o instanceof Player)|| o instanceof MenuObject))
			    if (!o.IsInWindow(w,h)  )
				toDeleteObjects.add(o);
		}
		objects.removeAll(toDeleteObjects);
	}


	private void DrawGameObjects(Graphics2D g2) {
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
                    dataModel.INSTANCE.score++;
					return;
				}
			}
		((Player)player).onPlatform = false;
	}

    private boolean IfInsideBoundariesOfThePlatform(gameObject o) {
        int margin = player.dim.width / 2 - 1;
        return ((o.x<player.x+margin)&&(o.x + o.dim.width + margin >player.x + player.dim.width));
    }

    private boolean IsOnSameLevelWithPlayer(gameObject o) {
        return (o.y == player.y + player.dim.height);
    }

    @Override
	public void actionPerformed(ActionEvent e) {
        dataModel.INSTANCE.time++;
        BornNewObjects();
	    ActForAllObjects();
	    SetPlayerOnPlatform();
        ValidateGameOver();
        ValidateMenu();
		repaint();
	}

    private void ValidateMenu()
    {
        if (objects.contains(menu))
            if (((MenuObject)menu).SelectedIndex == 0)
                return;

    }

    private void ValidateGameOver() {
        if (!dataModel.INSTANCE.isGameOver)
        if (player.y > dataModel.INSTANCE.getDimension().getHeight()-35)
        {
            dataModel.INSTANCE.isGameOver = true;
            objects.clear();
            MessageObject m = new MessageObject("Game over!");
            objects.add(m);
            objects.add(menu);
        }
    }


    public class Listener implements ComponentListener
    {
        @Override
        public void componentResized(ComponentEvent componentEvent) {
            dataModel.INSTANCE.setDimensions(componentEvent.getComponent().getSize());
            repaint();
        }

        @Override
        public void componentMoved(ComponentEvent componentEvent) {
        }

        @Override
        public void componentShown(ComponentEvent componentEvent) {
        }

        @Override
        public void componentHidden(ComponentEvent componentEvent) {
        }
    }

}
