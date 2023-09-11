package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.TissuRequisLaizeOption;

@Repository
public interface TissuRequisLaizeOptionDao extends Idao<TissuRequisLaizeOption, Integer> {
  List<TissuRequisLaizeOption> getTissuRequisLaizeOptionByRequisId(int id);

  @Query(value = "SELECT (MIN(trlo.LONGUEUR)) FROM TISSU_REQUIS_LAIZE_OPTION trlo WHERE trlo.REQUIS_ID = ?1 ",
			nativeQuery =	true)
  int getLongueurMinByRequis(int id);

}
