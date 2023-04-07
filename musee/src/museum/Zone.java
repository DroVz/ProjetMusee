package museum;

import java.util.List;

public class Zone {
	private int id;
	private String name;
	private int dim_x;
	private int dim_y;
	private int pos_x;
	private int pos_y;
	private Room room;
		
	private List<Spot> spots;

	public Zone(int id, String name, int dim_x, int dim_y, int pos_x, int pos_y, Room room) {
		this.id = id;
		this.name = name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.room = room;
	}
	
	public Zone(String name, int dim_x, int dim_y, int pos_x, int pos_y, Room room) {
		this.name = name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.room = room;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<Spot> getSpots() {
		return spots;
	}

	public void setSpots(List<Spot> spots) {
		this.spots = spots;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}



}
