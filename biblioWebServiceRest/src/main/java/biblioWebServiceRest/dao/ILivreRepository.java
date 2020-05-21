package biblioWebServiceRest.dao;



import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import biblioWebServiceRest.entities.Livre;




@Repository
public interface ILivreRepository extends JpaRepository <Livre, Long>, JpaSpecificationExecutor<Livre>{
	
	@Query("select l from Livre l where l.titre like %?1")
	Optional<Livre> findByTitre(String titre);

	

	
	
	




}
