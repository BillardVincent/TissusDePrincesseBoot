package fr.vbillard.tissusdeprincesseboot.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.model.TypeFourniture;

@Repository
public interface FournitureDao extends Idao<Fourniture, Integer>{

  boolean existsByReference(String reference);

  @Query(value = "SELECT SUM(fu.quantite) FROM FOURNITURE_USED fu INNER JOIN FOURNITURE t ON fu.FOURNITURE_ID = f.ID "
      + "INNER JOIN PROJET p ON p.ID = fu.PROJET_ID WHERE (p.STATUS = 'EN_COURS' OR p.STATUS = 'PLANIFIE' )"
      + "AND f.ID = ?1", nativeQuery = true)
  Integer quantiteUtilisee(int fournitureId);

  boolean existsFournitureByType(TypeFourniture typeFourniture);
}
