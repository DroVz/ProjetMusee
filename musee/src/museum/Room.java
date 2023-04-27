 package museum;

import java.util.ArrayList;
import java.util.List;

public class Room extends Area {
	private int id_room;
	private String name;
	private int floor;
	private int dim_z;
	
	/**
	 * constructor for Room if id_room is known
	 * @param id_room
	 * @param name
	 * @param floor
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 * @param pos_x
	 * @param pos_y
	 */
	public Room(int id_room, String name, int floor, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y) {
		super(pos_x, pos_y, dim_x, dim_y);
		this.id_room= id_room;
		this.name = name;
		this.floor = floor;
		this.dim_z = dim_z;
		
	}
	
	/**
	 * constructor for Room if id_room is unknown
	 * @param name
	 * @param floor
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 * @param pos_x
	 * @param pos_y
	 */
	public Room(String name, int floor, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y) {
		super(pos_x, pos_y, dim_x, dim_y);
		this.name = name;
		this.floor = floor;
		this.dim_z = dim_z;
	}

	public int getId_room() {
		return id_room;
	}
	
	public void setId_room(int id_room) {
		this.id_room = id_room;
	}
	
	public String getName() {
		return name;
	}

	public int getFloor() {
		return floor;
	}

	public int getDim_z() {
		return dim_z;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
