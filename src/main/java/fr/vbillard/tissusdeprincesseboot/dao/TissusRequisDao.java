package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.TissuRequis;

@Repository
public interface TissusRequisDao extends JpaRepository<TissuRequis, Integer> {

	List<TissuRequis> getAllByPatronId(int id);

}
