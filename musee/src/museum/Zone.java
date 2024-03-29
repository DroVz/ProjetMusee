package museum;

import java.util.List;

public class Zone extends Area {
	private int id;
	private String name;
	private Room room;
		
	private List<Spot> spots;

	public Zone(int id, String name, int dim_x, int dim_y, int pos_x, int pos_y, Room room) {
		super(pos_x, pos_y, dim_x, dim_y);
		this.id = id;
		this.name = name;
		this.room = room;
	}
	
	public Zone(String name, int dim_x, int dim_y, int pos_x, int pos_y, Room room) {
		super(pos_x, pos_y, dim_x, dim_y);
		this.name = name;
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

	@Override
	public String toString() {
		return this.name;
		
	}

	/**
	 * Vérifie que la zone est bien dans la salle parent.
	 * @return True si la zone est bien dans la salle parent.
	 */
	public boolean insideParent() {
		return this.getPos_x() + this.getDim_x() <= this.getRoom().getDim_x() 
				&& this.getPos_y() + this.getDim_y() <= this.getRoom().getDim_y();
	}
}
