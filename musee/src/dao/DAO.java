package dao;

import java.util.HashMap;

/**
 * Patron de conception DAO
 * @param <T> avec type générique T
 */
public abstract class DAO<T> {
	
	// TODO à supprimer
	// permet de tester le nombre d'appels read par une classe DAO pour vérifier qu'il n'y en a pas d'inutiles
	protected static int nbRead = 0;
	
	protected final HashMap<Integer, T> data = new HashMap<Integer, T>();
		
	/**
	 * Méthode de création d'un objet de type "T",
	 * peut être amené à injecter l'id créé dans le programme
	 * @param obj
	 * @return boolean 
	 */
	public abstract boolean create(T obj);

	/**
	 * Méthode pour effacer selon l'id de l'objet
	 * @param obj
	 * @return boolean 
	 */
	public abstract boolean delete(T obj);

	/**
	 * Méthode de mise à jour selon l'id de l'objet
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean update(T obj);

	/**
	 * Méthode de recherche des informations qui retourne un objet T
	 * @param id
	 * @return T
	 */
	public abstract T read(int id);	
}