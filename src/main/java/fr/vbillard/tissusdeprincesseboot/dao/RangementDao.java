package fr.vbillard.tissusdeprincesseboot.dao;

import fr.vbillard.tissusdeprincesseboot.model.Rangement;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RangementDao extends Idao<Rangement, Integer>{

    List<Rangement> getByConteneur_Id(int id, Sort sort);

    List<Rangement> getByConteneurRoot_Id(int id, Sort sort);
}
