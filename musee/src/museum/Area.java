package museum;

import java.util.ArrayList;
import java.util.List;

public abstract class Area {
	
	private List<Point> points = new ArrayList<Point>();
	
	private int pos_x;
	private int pos_y;
	private int dim_x;
	private int dim_y;

	public Area(int  pos_x, int pos_y, int dim_x, int dim_y) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.setPoints();
	}
	
	public int getPos_x() {
		return pos_x;
	}


	public void setPos_x(int pos_x) {
		this.pos_x = pos_x;
	}


	public int getPos_y() {
		return pos_y;
	}


	public void setPos_y(int pos_y) {
		this.pos_y = pos_y;
	}


	public int getDim_x() {
		return dim_x;
	}


	public void setDim_x(int dim_x) {
		this.dim_x = dim_x;
	}


	public int getDim_y() {
		return dim_y;
	}


	public void setDim_y(int dim_y) {
		this.dim_y = dim_y;
	}

	public void setPoints() {
		this.points.add(new Point(this.pos_x, this.pos_y));
		this.points.add(new Point(this.pos_x + this.dim_x, this.pos_y));
		this.points.add(new Point(this.pos_x, this.pos_y + this.dim_y));
		this.points.add(new Point(this.pos_x + this.dim_x, this.pos_y + this.dim_y));
	}
	
	public List<Point> getPoints(){
		return this.points;
	}
	
	/**
	 * Vérifie si une Area chevauche une des Areas de la liste "areas".
	 * @param areas
	 * @return
	 */
	public boolean overlaps(List<Area> areas) {
		boolean pointInside = false;
		
		// check other 
		for(Area area : areas) {
			if (this.overlapsOn(area)) {
				pointInside = true;
			}
		}
		
		//check Me
		for(Area area : areas) {
			if (area.overlapsOn(this)) {
				pointInside = true;
			}
		}
		
		return pointInside;
	}
	
	/**
	 * Vérifie si une Area en chevauche une autre.
	 * @param aera
	 * @return
	 */
	private boolean overlapsOn(Area aera) {
		boolean pointInside = false;
		
		for(Point otherPoint : aera.getPoints()) {
			if (this.checkPoint(otherPoint)) {
				pointInside = true;
			}
		}
		return pointInside;
	}
	
	/**
	 * Vérifie si un point est à l'intérieur d'une Area.
	 * @param point
	 * @return
	 */
	public boolean checkPoint(Point point) {
		boolean pointInside = false;
		int x = point.getPointX();
		int y = point.getPointY();
		
		if (x > this.points.get(0).getPointX() && x < this.points.get(1).getPointX() && 
				y > this.points.get(0).getPointY() && y < this.points.get(2).getPointY()) {
			pointInside = true;
		}
		
		return pointInside;
	}
}
