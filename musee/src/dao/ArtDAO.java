package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import museum.Art;
import museum.ArtStatus;
import museum.ArtType;
import museum.Author;

public class ArtDAO extends DAO<Art> {
	
	private static final String TABLE = "art";
	private static final String PK = "id_art";
	private static final String TYPE = "ref_art_type";
	private static final String AUTHOR = "ref_author";
	private static final String STATUS = "ref_art_status";
	private static final String CODE = "art_code";
	private static final String TITLE = "art_title";
	private static final String DATE = "creation_date";
	private static final String MATERIALS = "materials";
	private static final String DIMX = "dim_x";
	private static final String DIMY = "dim_y";
	private static final String DIMZ = "dim_z";
	private static final String IMAGE = "image";
	private static final String OWNER = "owner";
	
	private static ArtDAO instance=null;
	
	// hashmap dégradée pour stocker les œuvres sans l'image
	private final HashMap<Integer, Art> dataLight = new HashMap<Integer, Art>();
	
	public static ArtDAO getInstance(){
		if (instance==null){
			instance = new ArtDAO();
		}
		return instance;
	}
	
	private ArtDAO() {
		super();
	}

	@Override
	public boolean create(Art art) {
		boolean success = true;
		try {
			String requete = "INSERT INTO "+TABLE+" ("+TYPE+", "+AUTHOR+", "+STATUS+", "+CODE+", "+
					TITLE+", "+DATE+", "+MATERIALS+", "+DIMX+", "+DIMY+", "+DIMZ+ ", "+IMAGE +", "+OWNER+")"
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, art.getArt_type().getId_Art_type());
			pst.setInt(2, art.getAuthor().getId_author());
			pst.setInt(3, art.getArt_status().getId_art_status());
			pst.setString(4, art.getArt_code());
			pst.setString(5, art.getArt_title());
			pst.setString(6, art.getCreation_date());
			pst.setString(7, art.getMaterials());
			pst.setInt(8, art.getDim_x());
			pst.setInt(9, art.getDim_y());
			pst.setInt(10, art.getDim_z());
			pst.setBytes(11, art.getImage());
			pst.setBoolean(12, art.isOwner());
			pst.executeUpdate();
			// on récupère la clé générée et on la pousse dans l'objet initial
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next()) {
				art.setId_art(rs.getInt(1));
			}
			data.put(art.getId_art(), art);

		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;		
	}

	@Override
	public boolean delete(Art art) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Art art) {		
		boolean success = true;
		try {
			String requete = "UPDATE "+TABLE+" SET "+ TYPE+"=?, "+AUTHOR+"=?, "+STATUS+
					"=?, "+CODE+"=?, "+TITLE+"=?, "+DATE+"=?, "+MATERIALS+"=?, "+DIMX+"=?, "+
					DIMY+"=?, "+DIMZ+"=?, "+IMAGE+"=?, "+OWNER+"=? WHERE "+PK+"= ?";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, art.getArt_type().getId_Art_type());
			pst.setInt(2, art.getAuthor().getId_author());
			pst.setInt(3, art.getArt_status().getId_art_status());
			pst.setString(4, art.getArt_code());
			pst.setString(5, art.getArt_title());
			pst.setString(6, art.getCreation_date());
			pst.setString(7, art.getMaterials());
			pst.setInt(8, art.getDim_x());
			pst.setInt(9, art.getDim_y());
			pst.setInt(10, art.getDim_z());
			pst.setBytes(11, art.getImage());
			pst.setInt(12, art.getId_art());
			pst.setBoolean(13, art.isOwner());
			pst.executeUpdate();
			data.put(art.getId_art(), art);
		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;
	}
 
	/**
	 * retourne les données complètes de l'œuvre (image incluse)
	 * @param id
	 * @return
	 */
	@Override
	public Art read(int id) {
		Art art = null;
		if (data.containsKey(id)) {
			art=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				int ref_art_type = rs.getInt(TYPE);
				int ref_author = rs.getInt(AUTHOR);
				int ref_art_status = rs.getInt(STATUS);
				String code = rs.getString(CODE);
				String title = rs.getString(TITLE);
				String date = rs.getString(DATE);
				String materials = rs.getString(MATERIALS);
				int dim_x = rs.getInt(DIMX);
				int dim_y = rs.getInt(DIMY);
				int dim_z = rs.getInt(DIMZ);
				byte[] image = rs.getBytes(IMAGE);
				boolean owner = rs.getBoolean(OWNER);
				ArtType art_type = ArtTypeDAO.getInstance().read(ref_art_type);
				Author author = AuthorDAO.getInstance().read(ref_author);
				ArtStatus art_status = ArtStatusDAO.getInstance().read(ref_art_status);
				art = new Art(id, code, title, date, materials, dim_x, dim_y, dim_z,
						image, author, art_status, art_type, owner);
				data.put(id, art);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return art;
	}
	
	/**
	 * retourne les données partielles de l'œuvre (image non incluse)
	 * @param id
	 * @return
	 */
	public Art readLight(int id) {
		Art artLight = null;
		if (dataLight.containsKey(id)) {
			artLight=dataLight.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				int ref_art_type = rs.getInt(TYPE);
				int ref_author = rs.getInt(AUTHOR);
				int ref_art_status = rs.getInt(STATUS);
				String code = rs.getString(CODE);
				String title = rs.getString(TITLE);
				String date = rs.getString(DATE);
				String materials = rs.getString(MATERIALS);
				int dim_x = rs.getInt(DIMX);
				int dim_y = rs.getInt(DIMY);
				int dim_z = rs.getInt(DIMZ);
				boolean owner = rs.getBoolean(OWNER);
				ArtType art_type = ArtTypeDAO.getInstance().read(ref_art_type);
				Author author = AuthorDAO.getInstance().read(ref_author);
				ArtStatus art_status = ArtStatusDAO.getInstance().read(ref_art_status);
				artLight = new Art(id, code, title, date, materials, dim_x, dim_y, dim_z,
						null, author, art_status, art_type, owner);
				dataLight.put(id, artLight);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return artLight;
	}
	
	/**
	 * retourne la liste d'œuvres en BD (version light - sans photo)
	 * @return
	 */
	public List<Art> readAll() {
		List<Art> art_objects = new ArrayList<Art>();
		Art art = null;
		try {			
			String requete = "SELECT * FROM " + TABLE;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_art = rs.getInt(1);
				art = ArtDAO.getInstance().readLight(id_art);
				art_objects.add(art);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return art_objects;		
	}
	
	/**
	 * retourne la liste d'œuvres en réserve (version light - sans photo)
	 * @return
	 */
	public List<Art> readAllAvailable() {
		List<Art> art_objects = new ArrayList<Art>();
		Art art = null;
		try {			
			String requete = "SELECT * FROM " + TABLE + " JOIN art_status ON (art.ref_art_status = "
					+ "art_status.id_art_status) WHERE art_status.name = 'En réserve'";
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_art = rs.getInt(1);
				art = ArtDAO.getInstance().readLight(id_art);
				art_objects.add(art);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return art_objects;		
	}
}
