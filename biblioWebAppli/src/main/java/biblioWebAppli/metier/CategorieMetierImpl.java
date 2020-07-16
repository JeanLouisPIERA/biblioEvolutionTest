/**
 * Classe permettant l'implémentation des méthodes métiers de l'application Web 
 * Grace à RestTemplate, ces méthodes l'application Web consomme en tant que client l'API REST biblioWebServiceRest
 */
package biblioWebAppli.metier;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import biblioWebAppli.criteria.CategorieCriteria;
import biblioWebAppli.dto.CategorieDTO;
import biblioWebAppli.objets.Categorie;

import biblioWebServiceRest.exceptions.EntityNotDeletableException;





/**
 * @author jeanl
 *
 */
@Service
public class CategorieMetierImpl implements ICategorieMetier{
			    
	    @Autowired
	    private RestTemplate restTemplate;
	    
	    @Value("${application.uRLCategorie}")
		private String uRL;
	    
	    
	    /**
	     * Permet d'obtenir une sélection paginée des catégories
	     * @param categorieCriteria
	     * @param page
	     * @param size
	     * @return
	     */
	    @Override
		public Page<Categorie> searchByCriteria(CategorieCriteria categorieCriteria, int page, int size) {
	    	
	    	HttpHeaders headers = new HttpHeaders();
	    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
	    	
	    	System.out.println("uRL:"+uRL);
	    	
	    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRL)
	    	        .queryParam("nomCategorie", categorieCriteria.getNomCategorie())
	    	        .queryParam("page", page)
	    	        .queryParam("size", size);
	    	
	    	System.out.println("uri =" + builder.toUriString());

	    	HttpEntity<?> entity = new HttpEntity<>(headers);
	    	
	    	ResponseEntity<RestResponsePage<Categorie>> categories = restTemplate.exchange
	    			(builder.toUriString(), 
    				HttpMethod.GET,
    				entity,
	    			new ParameterizedTypeReference<RestResponsePage<Categorie>>(){});
	        Page<Categorie> pageCategorie = categories.getBody();
	        
	            	
	        return pageCategorie;
	    	
	    	
	    	
	    }
	    
	   

	    /**
	     * @throws biblioWebAppli.exceptions.EntityAlreadyExistsException 
		 * Permet de créer une nouvelle catégorie de livre
		 * @param categorieDTO
		 * @return
	     * @throws  
		 */
	    public Categorie createCategorie(CategorieDTO categorieDTO) {
	    	
	    	HttpHeaders headers = new HttpHeaders();
	    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    	headers.setContentType(MediaType.APPLICATION_JSON);

	    	HttpEntity<CategorieDTO> requestEntity = 
	    	     new HttpEntity<>(categorieDTO, headers);
	    	ResponseEntity<Categorie> response = restTemplate.exchange(uRL, HttpMethod.POST, requestEntity, 
				              Categorie.class);
			System.out.println(response.getStatusCodeValue());
			Categorie categorieToCreate = response.getBody();
		
	    	return categorieToCreate;
	    }
	    
	    /**
		 * Permet de supprimer une categorie de livre
		 * @param numCategorie
	     * @throws EntityNotDeletableException 
		 */
	   
	    public String delete(Long numCategorie) {
	    	//restTemplate.delete(uRL+"/"+numCategorie);   
	    	HttpHeaders headers = new HttpHeaders();
	    	headers.setAccept(Arrays.asList(MediaType.ALL));
	    	headers.setContentType(MediaType.TEXT_PLAIN);
	    	
	    	HttpEntity<?> requestEntity = 
	       	     new HttpEntity<>(headers);
			
			String url = uRL+"/"+numCategorie;
	    	
			ResponseEntity<String> response = restTemplate.exchange(url , HttpMethod.DELETE, requestEntity, String.class);
			
			System.out.println("response:"+ response.toString()); 
			
			return response.getBody(); 
	    	
	     }
	     	
	}
	    



