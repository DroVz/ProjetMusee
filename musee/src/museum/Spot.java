package museum;

public class Spot {
	
	private int id;
	private String name;
	private int dim_x;
	private int dim_y;
	private int dim_z;
	private int pos_x;
	private int pos_y;
	private int pos_z;
	private Art art;
	private Zone zone;

	// Constructeur 
	public Spot(int id, String name, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y, int pos_z, Art art, Zone zone) {
		this.id = id;
		this.name = name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.dim_z = dim_z;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.pos_z = pos_z;
		this.art = art;
		this.zone = zone;
	}
	// Constructeur Sans id  
	public Spot( String name, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y, int pos_z, Zone zone, Art art) {
		this.name = name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.dim_z = dim_z;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.pos_z = pos_z;
		this.zone = zone;
		this.art = art;
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

	public int getDim_z() {
		return dim_z;
	}

	public void setDim_z(int dim_z) {
		this.dim_z = dim_z;
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

	public int getPos_z() {
		return pos_z;
	}

	public void setPos_z(int pos_z) {
		this.pos_z = pos_z;
	}

	public Art getArt() {
		return art;
	}

	public void setArt(Art art) {
		this.art = art;
	}
	
	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}


	
}
