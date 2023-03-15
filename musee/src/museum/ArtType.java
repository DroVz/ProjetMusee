package museum;

public class ArtType {
	private int id_art_type;
	private String name;
	
	/**
	 * constructor for ArtType
	 * @param art_type
	 * @param name
	 */
	public ArtType(int art_type, String name) {
		this.id_art_type = art_type;
		this.name = name;
	}

	public int getId_Art_type() {
		return id_art_type;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
