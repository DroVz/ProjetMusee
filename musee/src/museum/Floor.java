 package museum;

public class Floor {
	private int id_floor;
	private String name;
	private int dim_x;
	private int dim_y;
	private int dim_z;
	private int pos_x;
	private int pos_y;
	
	/**
	 * constructor for Floor if id_floor is known
	 * @param id_floor
	 * @param name
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 * @param pos_x
	 * @param pos_y
	 */
	public Floor(int id_floor, String name, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y) {
		this.id_floor= id_floor;
		this.name = name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.dim_z = dim_z;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
	}
	
	/**
	 * constructor for Floor if id_floor is unknown
	 * @param name
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 * @param pos_x
	 * @param pos_y
	 */
	public Floor(String name, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y) {
		this.name = name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.dim_z = dim_z;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
	}

	public int getId_floor() {
		return id_floor;
	}
	
	public void setId_floor(int id_floor) {
		this.id_floor = id_floor;
	}

	public String getName() {
		return name;
	}

	public int getDim_x() {
		return dim_x;
	}

	public int getDim_y() {
		return dim_y;
	}

	public int getDim_z() {
		return dim_z;
	}

	public int getPos_x() {
		return pos_x;
	}

	public int getPos_y() {
		return pos_y;
	}
	
	@Override
	public String toString() {
		return "Room [id=" + id_floor + ", nom=" + name + ", dim_x=" + dim_x + ", dim_y=" + dim_y +
				", dim_z=" + dim_z + ", pos_x=" + pos_x + ", pos_y=" + pos_y + "]";
	}
}
