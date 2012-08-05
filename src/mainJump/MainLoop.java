package mainJump;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JFrame;

public class MainLoop extends JFrame{

	
	public MainLoop()
	{
		
		   add(new VisibleArea());
	        setTitle("Game");
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setSize(300, 280);
	        setLocationRelativeTo(null);
	        setVisible(true);
	        setResizable(false);
	       	        
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainLoop loop = new MainLoop();
	}


}
