/**
 * Interface définissant les méthodes métier de l'appli Web 
 */
package biblioWebAppli.metier;

import org.springframework.data.domain.Page;


import biblioWebAppli.criteria.CategorieCriteria;
import biblioWebAppli.dto.CategorieDTO;
import biblioWebAppli.entities.Categorie;
import biblioWebAppli.exceptions.EntityAlreadyExistsException;
import biblioWebServiceRest.exceptions.EntityNotDeletableException;






/**
 * @author jeanl
 *
 */
public interface ICategorieMetier {
		
	/**
	 * Permet d'afficher les catégories existantes de livre
	 * @param categorieCriteria
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Categorie> searchByCriteria(CategorieCriteria categorieCriteria, int page, int size);
	
	
	/**
	 * Permet de créer une nouvelle catégorie de livre
	 * @param categorieDTO
	 * @return
	 * @throws EntityAlreadyExistsException 
	 */
	public Categorie createCategorie(CategorieDTO categorieDTO) throws EntityAlreadyExistsException;
	
	/**
	 * Permet de supprimer une categorie de livre
	 * @param numCategorie
	 * @throws EntityNotDeletableException 
	 */
	public void delete(Long numCategorie) throws EntityNotDeletableException;
}
