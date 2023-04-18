package museum;

public class Spot extends Area {
	
	private int id;
	private String name;
	private int dim_z;
	private int pos_z;
	private Art art;
	private Zone zone;

	// Constructeur 
	public Spot(int id, String name, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y, int pos_z, Art art, Zone zone) {
		super(pos_x, pos_y, dim_x, dim_y);
		this.id = id;
		this.name = name;
		this.dim_z = dim_z;
		this.pos_z = pos_z;
		this.art = art;
		this.zone = zone;
	}
	// Constructeur Sans id  
	public Spot( String name, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y, int pos_z, Zone zone, Art art) {
		super(pos_x, pos_y, dim_x, dim_y);
		this.name = name;
		this.dim_z = dim_z;
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

	public int getDim_z() {
		return dim_z;
	}

	public void setDim_z(int dim_z) {
		this.dim_z = dim_z;
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
