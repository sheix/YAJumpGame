package mainJump;

import java.awt.Dimension;

public enum dataModel {
	INSTANCE;
	
	private Dimension _dim;
	
	public void setDimensions(Dimension dim)
	{
		_dim = dim;
	}
	
	public int getWidthPercent(int percent) {
		return _dim.width/100*percent;
	}
	
	public int getHeightPercent(int percent) {
		return _dim.height/100*percent;
	}


    public Dimension getDimension() {
        return _dim;
    }

    public boolean isGameOver;


    public int score;
    public int time;
}
