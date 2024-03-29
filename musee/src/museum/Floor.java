 package museum;

import java.util.ArrayList;
import java.util.List;

public class Floor extends Area {
	private int id_floor;
	private String name;
	private int dim_z;
	private List<Room> rooms;
	
	/**
	 * constructor for Floor if id_floor is known
	 * @param id_floor
	 * @param name
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 */
	public Floor(int id_floor, String name, int dim_x, int dim_y, int dim_z) {
		super(0, 0, dim_x, dim_y);
		this.id_floor= id_floor;
		this.name = name;
		this.dim_z = dim_z;
	}
	
	/**
	 * constructor for Floor if id_floor is unknown
	 * @param name
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 */
	public Floor(String name, int dim_x, int dim_y, int dim_z) {
		super(0, 0, dim_x, dim_y);
		this.name = name;
		this.dim_z = dim_z;
		this.rooms = new ArrayList<>();
	}

	public int getId_floor() {
		return this.id_floor;
	}
	
	public void setId_floor(int id_floor) {
		this.id_floor = id_floor;
	}

	public String getName() {
		return this.name;
	}

	public int getDim_z() {
		return this.dim_z;
	}
	
	public void setDim_z(int Dim_z) {
		this.dim_z = Dim_z ;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	@Override
	public String toString() {
		return this.getName();
	}
	
	public List<Room> getRooms(){
		return this.rooms;
	}
	
	/**
	 * Vérifie que l'étage est bien dans le conteneur supérieur.
	 * @return True si l'étage est bien dans le conteneur supérieur.
	 */
	public Boolean insidePane(int planWidth, int planHeight, double ratio) {
		 return (this.getPos_x()* ratio) + (this.getDim_x() * ratio)<= planWidth 
		 && (this.getPos_y()*ratio) + (this.getDim_y() * ratio)<= planHeight;

		 }
	
}