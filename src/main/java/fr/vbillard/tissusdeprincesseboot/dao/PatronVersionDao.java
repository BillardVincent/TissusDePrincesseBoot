package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.PatronVersion;

@Repository
public interface PatronVersionDao extends Idao<PatronVersion, Integer> {

  List<PatronVersion> getByPatronId(int id);
}
