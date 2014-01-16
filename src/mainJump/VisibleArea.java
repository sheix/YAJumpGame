package mainJump;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

public class VisibleArea extends JPanel implements ActionListener {

	Timer timer;
	private ArrayList<gameObject> objects;
	gameObject player;
    gameObject menu;
    Graphics2D g2;
    double w,h;

    private final PlatformBuilder platformBuilder;

    public VisibleArea()
	{
        platformBuilder = new PlatformBuilder(this);
        objects = new ArrayList<gameObject>();
        ShowMenu();
        StartGame();
    }

    private void ShowMenu()
    {
        ArrayList<String> items = new ArrayList<String>();
        items.add("Start Game");
        items.add("Exit");
        menu = new MenuObject(items);
        objects.add(menu);
        addKeyListener(new TMenuAdapter());
        InitializeTimer();
    }

    private void StartGame() {
        dataModel.INSTANCE.score = 1;
        dataModel.INSTANCE.time = 1;
        setFocusable(true);
        setDoubleBuffered(true);
    }

    private void InitializeNewGame() {
        addKeyListener(new TAdapter());
        this.addComponentListener(new Listener());
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

    private class TMenuAdapter extends KeyAdapter{
        public void keyReleased(KeyEvent e) {
            menu.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            menu.keyPressed(e);
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
      g2 = (Graphics2D) g;
      RenderingHints rh =
            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
      rh.put(RenderingHints.KEY_RENDERING,
             RenderingHints.VALUE_RENDER_QUALITY);
      g2.setRenderingHints(rh);

      Dimension size = getSize();
      w = size.getWidth();
      h = size.getHeight();

      DrawGameObjects(g2);

      Toolkit.getDefaultToolkit().sync();
      g.dispose();
    }
	
	
    private void ActForAllObjects() {
        boolean setPlayer = false;
    	for (gameObject o : objects) {
			o.OnTimer();
            if (o instanceof Platform)
            {
                if (IsOnSameLevelWithPlayer(o) && IfInsideBoundariesOfThePlatform(o))
                {
                    ((Player)player).onPlatform = true;
                    setPlayer = true;
                    dataModel.INSTANCE.score++;
                }
            }
    	}
        if (!setPlayer)
            ((Player)player).onPlatform = false;
    }


	private void BornNewObjects() {
        if (dataModel.INSTANCE.time == 2)
            platformBuilder.MakeInitialPlatforms();
        platformBuilder.TryMakePlatform();
    }

    private void KillWrongObjects(double w, double h) {
		ArrayList<gameObject> newObjects = new ArrayList<gameObject>();
		for (gameObject o : objects) {
            if (!((o instanceof ScoreObject) || (o instanceof Player)|| o instanceof MenuObject))
			    if (!o.IsInWindow(w,h))
				continue;
            newObjects.add(o);
		}
		objects = newObjects;
	}


	private void DrawGameObjects(Graphics2D g2) {
    	for (gameObject o : objects) {
			o.Draw(g2);
		}
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
        if (IsMenuOn())
            ValidateMenu();
        else
        {
            dataModel.INSTANCE.time++;
            BornNewObjects();
            KillWrongObjects(w, h);
	        ActForAllObjects();
	        ValidateGameOver();
        }
		repaint();
	}

    private boolean IsMenuOn()
    {
        return objects.contains(menu);
    }

    private void ValidateMenu()
    {
        if (IsMenuOn())
            if (((MenuObject)menu).isSelected)
            {
               if (((MenuObject)menu).SelectedIndex == 0)
                   InitializeNewGame();
            }
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
